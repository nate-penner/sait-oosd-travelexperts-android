package oosd.sait.travelexperts.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A data source for obtaining agent information from an API, which implements {@link DataResource}
 * (see for documentation of interface methods)
 * @see AgentMin for information about the type of data used by this resource
 * @apiNote Only methods used so far in this project are implemented
 * @author Nate Penner
 * */
public class AgentMinResource implements DataResource<AgentMin, Integer> {
    @Override
    public int insert(AgentMin data) {
        return 0;
    }

    @Override
    public AgentMin getById(Integer id) {
        return null;
    }

    @Override
    public int update(AgentMin data) {
        return 0;
    }

    @Override
    public int delete(AgentMin data) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public Collection<AgentMin> getList() {
        APIRequest request = new APIRequest(
                API.get("agents"),
                "/listitem",
                "GET",
                "application/json",
                "application/json"
        );

        String data = request.send();

        Log.d("nate", "agent list: " + data);
        List<AgentMin> agentsList = new ArrayList<>();
        try {
            JSONArray agents = new JSONArray(data);

            for (int i = 0; i < agents.length(); i++) {
                JSONObject agentData = agents.getJSONObject(i);
                Log.d("nate", "loop " + i);
                Log.d("nate", "id is " + agentData.getString("id"));
                Log.d("nate", "id is " + agentData.getInt("id"));
                AgentMin agent = new AgentMin(
                        agentData.getInt("id"),
                        agentData.getString("agtFirstName"),
                        agentData.getString("agtLastName")
                );
                agentsList.add(agent);
            }
        } catch (JSONException e) {
            Log.d("nate", "something terrible happened: " + e.getMessage());
            e.printStackTrace();
        }

        return agentsList;
    }
}
