package cr.ac.una.attendancerecorderws.util;

public class ReportUtils {

    public static byte[] toPrimitive(Byte[] boxed) {
        if (boxed == null) {
            return null;
        }
        byte[] result = new byte[boxed.length];
        for (int i = 0; i < boxed.length; i++) {
            result[i] = boxed[i];
        }
        return result;
    }
    
}
