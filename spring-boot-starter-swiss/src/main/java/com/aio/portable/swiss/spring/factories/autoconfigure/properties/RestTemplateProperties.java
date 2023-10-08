package com.aio.portable.swiss.spring.factories.autoconfigure.properties;

public class RestTemplateProperties {
    private Agent agent;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }


    public static class Agent {
        private boolean enabled = false;
        private String host = "127.0.0.1";
        private int port = 8888;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
