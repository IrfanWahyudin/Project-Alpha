package com.delta.irfan.alpha.dataaccess;

import com.delta.irfan.alpha.datastreamer.HttpGetData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by irfan on 12/13/2015.
 */
public class AgentBusinessLogic {
    public class DetailAgent {
        public DetailAgent(){};
        public DetailAgent(String id, String name, String pic, String address){
            this.setId(id);
            this.setName(name);
            this.setPic(pic);
            this.setAddress(address);
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        String id = "";
        String name = "";
        String pic = "";
        String address = "";
    }

    DetailAgent [] detailAgent ;

    public DetailAgent [] retrieveAgents(String param) throws IOException, JSONException {
        String responseAgents = "";
        String [] listAgent = null;
        int numOfAgent = 0;
        HttpGetData httpGetData = new HttpGetData();
        String sURL = "http://192.168.0.104/project_alpha/v1_0/index.php/api/Agents/retrieve_agents/";
        String sParam = "param/" + param + "/format/json";
        responseAgents = httpGetData.GetData(sURL, sParam);

        JSONObject agents = new JSONObject(responseAgents);
        JSONArray agent = agents.getJSONArray("agents");
        numOfAgent = agent.length();


        detailAgent = new DetailAgent[numOfAgent];
        for (Integer i=0; i < numOfAgent; i++){
            JSONObject agentDetail = agent.getJSONObject(i);
            detailAgent[i] = new DetailAgent();
            detailAgent[i].setAddress(agentDetail.getString("address"));
            detailAgent[i].setName(agentDetail.getString("name"));
            detailAgent[i].setId(agentDetail.getString("id"));
//            detailAgent[i].setPic(agentDetail.getString("pic"));
        }

        return detailAgent;

    }
}