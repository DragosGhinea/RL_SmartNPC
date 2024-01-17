package ro.smartnpc.algorithms.python;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.smartnpc.algorithms.states.RelativeCoordinatesState;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeepQLearningProxy {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static synchronized void init(int numberOfAgents, int numberOfActions) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("numberOfAgents", numberOfAgents);
        data.put("numberOfActions", numberOfActions);
        String jsonData = objectMapper.writeValueAsString(data);

        PythonConnection.getInstance().getOutputStream().write(jsonData.getBytes());
    }

    public static synchronized void sendToBuffer(RelativeCoordinatesState state, int actionTaken, double reward, RelativeCoordinatesState nextState) {

    }

    public static synchronized int getNextAction(RelativeCoordinatesState state) {

        return -1;
    }
}
