import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;


//TODO: The tests are currently placeholders, need more work done

class CycleTimeTest {

    @Test
    void testGetCycleTimePerTask() {
        // Mock the static method in Tasks class
        try (MockedStatic<Tasks> mockedTasks = Mockito.mockStatic(Tasks.class)) {
            // Mock response from getClosedTasks
            List<JsonNode> closedTasks = createMockClosedTasks();

            // Mocking the static method call
            mockedTasks.when(() -> Tasks.getClosedTasks(anyInt(), anyString(), anyString())).thenReturn(closedTasks);

            // Call the method to be tested
            Map<String, Long> cycleTimeMap = LeadTime.getLeadTimePerTask(123, "token", "endpoint");

            // Assertions
            assertEquals(2, cycleTimeMap.size());
            assertEquals(4, cycleTimeMap.get("1"));
            assertEquals(0, cycleTimeMap.get("2"));
        }
    }

    // Helper method to create a mock list of closed tasks
    private List<JsonNode> createMockClosedTasks() {
        JsonNode task1 = createMockTask("4", "2024-01-31T10:00:00.000Z", "2024-02-03T15:30:00.000Z");
        JsonNode task2 = createMockTask("0", "2024-02-04T12:00:00.000Z", "2024-02-04T14:45:00.000Z");
        return List.of(task1, task2);
    }

    // Helper method to create a mock task
    private JsonNode createMockTask(String id, String startDate, String finishedDate) {
        JsonNode mockJsonNode = mock(JsonNode.class);
        return mockJsonNode;
    }
}
