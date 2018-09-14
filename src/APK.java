import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class APK {
    public static void main(String args[]) {
        APK apk = new APK();
        apk.runAPK();
    }
    //
    public void runAPK(){
        int count = 0;
        try {
            count = read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        String input = "";
        while (!input.equals("stop")) {
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            //
            count++;
            System.out.print(count);
        }
        //
        try {
            write(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //
    public void write(Integer count) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("apk.txt");
        File file = new File(filePath.getFile());
        BufferedWriter thingThatWrites = new BufferedWriter(new FileWriter(file));
            try {
                thingThatWrites.write(count);
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println("File Saved!");
        thingThatWrites.flush();
        thingThatWrites.close();
    }
    //
    public Integer read() throws IOException {
        List<List<String>> combined = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("apk.txt");
        File file = new File(filePath.getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        Integer count =0;
        String line = br.readLine();
        if(line !=null) {
            count = Integer.parseInt(line);
        }
        //
        return count;
    }
}
