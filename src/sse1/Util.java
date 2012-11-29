/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

public class Util {

    /**
     * Turns array of bytes into string
     *
     * @param buf	Array of bytes to convert to hex string
     * @return	Generated hex string
     */
    public static String hexArray(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }

            strbuf.append(Integer.toString((int) buf[i] & 0xff, 16));
        }

        return strbuf.toString();
    }
}
