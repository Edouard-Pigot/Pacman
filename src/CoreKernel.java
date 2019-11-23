public class CoreKernel {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(MapCreator.class);
            }
        }.start();
        MapCreator startUpTest = MapCreator.waitForStartUpTest();
    }
}
