package Server;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by kesso on 25.04.17.
 */
public class MainLogic {
    public static String coding(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;
    }
}
