package ro.smartnpc.algorithms.python;

import ro.smartnpc.SmartNPC;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class PythonConnection {

    private static PythonConnection instance = null;

    private final static String SERVER_HOST = "127.0.0.1";
    private final static int SERVER_PORT = 25567;

    public static synchronized PythonConnection getInstance() {
        if (instance == null) {
            instance = new PythonConnection();
        }
        return instance;
    }

    private Socket socket;

    private InputStream inputStream;
    private OutputStream outputStream;

    private PythonConnection() {

    }

    public boolean connect() {
        try {
            if (socket == null || socket.isClosed()) {
                socket = new Socket(SERVER_HOST, SERVER_PORT);

                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                socket.setSoTimeout(1000);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            SmartNPC.getInstance().getLogger().warning("Failed to connect to python server");
        }
        return false;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
