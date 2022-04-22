public class ServerHandler implements Runnable{

    private ServerHandler serverHandler;

    @Override
    public void run() {
    }


    /**
     * The handler object responsible for communicating with the server.
     * @return The server handler.
     */
    public ServerHandler getServerHandler()
    {
        return serverHandler;
    }
}
