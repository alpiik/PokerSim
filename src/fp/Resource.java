package fp;

public class Resource {

    private static boolean isOpen = false;
    private static String writtenData = "";

    public void open() {
        System.out.println("Resource opened.");
        isOpen = true;
    }
    public void close() {
        System.out.println("Resource closed.");
        isOpen = false;
    }
    public void write(String data) {
        if (!isOpen) {
            throw new IllegalStateException("Resource is not open");
        }
        System.out.println("Writing data: " + data);
        writtenData += data;
    }
    public static String getWrittenData() {
        return writtenData;
    }
    public static boolean isOpen() {
        return isOpen;
    }
    public static void reset() {
        isOpen = false;
        writtenData = "";
        System.out.println("Resource state reset.");
    }
}