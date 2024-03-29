package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * APIs to pull User Story Details 
 */

public class UserStoryUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    
    public static List<JsonNode> getAllUserStories(String authToken,String TAIGA_API_ENDPOINT,int projectId, String sprint) {
        List<JsonNode> statList = new ArrayList<>();
        int milestoneId = SprintUtils.getSprintIdByName(authToken,TAIGA_API_ENDPOINT,projectId,sprint);
        String endpoint = TAIGA_API_ENDPOINT + "/userstories?milestone="+milestoneId;

        try{
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(endpoint);
            request.setHeader("Authorization", "Bearer " + authToken);
            request.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JsonNode userStories = objectMapper.readTree(result.toString());
            for (JsonNode userStory : userStories) {
                statList.add(userStory);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return statList;
    }

    public static List<JsonNode> getAllIssues(String authToken,String TAIGA_API_ENDPOINT,int projectId, String sprint) {
        List<JsonNode> statList = new ArrayList<>();
        int milestoneId = SprintUtils.getSprintIdByName(authToken,TAIGA_API_ENDPOINT,projectId,sprint);
        String endpoint = TAIGA_API_ENDPOINT + "/issues?milestone="+milestoneId;

        try{
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(endpoint);
            request.setHeader("Authorization", "Bearer " + authToken);
            request.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JsonNode issues = objectMapper.readTree(result.toString());
            for (JsonNode issue : issues) {
                statList.add(issue);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return statList;
    }

    public static JsonNode getUserStoryHistory(int projectId, String authToken, String TAIGA_API_ENDPOINT, String usId){

        String endpoint = TAIGA_API_ENDPOINT + "/history/userstory/" + usId;
        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        String responseJson = HTTPRequest.sendHttpRequest(request);
        System.out.println(responseJson);

        JsonNode usNode = null;
        try {
            usNode = objectMapper.readTree(responseJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usNode;
    }
    public static JsonNode getIssueHistory(int projectId, String authToken, String TAIGA_API_ENDPOINT, String issueId){

        JsonNode issueHistory = null;
        String endpoint = TAIGA_API_ENDPOINT + "/history/issues/" + issueId;
        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        String responseJson = HTTPRequest.sendHttpRequest(request);
        System.out.println(responseJson);

        try {
            issueHistory = objectMapper.readTree(responseJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issueHistory;
    }
    public static List<String> getAllUserStoryIdsInProject(int projectId, String authToken, String TAIGA_API_ENDPOINT){
        String endpoint = TAIGA_API_ENDPOINT + "/userstories?project="+projectId;
        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        String responseJson = HTTPRequest.sendHttpRequest(request);
        List<String> userStories = new ArrayList<>();

        try {
            JsonNode tasksNode = objectMapper.readTree(responseJson);

            for (JsonNode taskNode : tasksNode) {
                userStories.add(taskNode.get("id").toString());
            }
            return userStories;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
