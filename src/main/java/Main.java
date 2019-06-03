import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Main {

    private static String readToCheckStrings() {


        StringBuilder stringBuilder = new StringBuilder();

        File file = new File("/Users/diyuanwang/Desktop/DFASearch/src/main/resources/tocheck.txt");
        InputStreamReader read = null;
        try {
            read = new InputStreamReader(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        try {
            if (file.isFile() && file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt;
                while ((txt = bufferedReader.readLine()) != null) {
                    stringBuilder.append(txt);
                }
            } else {
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     *
     * @author chenming
     */
    private static Set<String> readSensitiveWordFile() throws Exception {

        Set<String> set = null;

        File file = new File("/Users/diyuanwang/Desktop/DFASearch/src/main/resources/words.txt");
        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
        try {
            if (file.isFile() && file.exists()) {
                set = new HashSet<>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt;
                while ((txt = bufferedReader.readLine()) != null) {    //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
            } else {
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            read.close();
        }
        return set;
    }

    public static void main(String[] args) {

        try {
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();

            SensitiveWordMatcher filter = new SensitiveWordMatcher(keyWordSet);
            String string = readToCheckStrings();

            System.out.println("待检测语句字数：" + string.length());

            long beginTime = System.currentTimeMillis();
            Set<String> set = filter.matches(string, false);
            long endTime = System.currentTimeMillis();
            System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
            System.out.println("总共消耗时间为：" + (endTime - beginTime));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
