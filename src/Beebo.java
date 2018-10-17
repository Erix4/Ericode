import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Beebo {
    //
    public void main(String args[]){
        //
        List<List<String>> read = new ArrayList<>();//Double list for read
        List<String> covs = new ArrayList<>();
        List<String> aas = new ArrayList<>();
        List<String> qsa = new ArrayList<>();
        List<String> qsb = new ArrayList<>();
        List<String> qsc = new ArrayList<>();
        List<String> as = new ArrayList<>();
        //
        try {
            read = read();
            covs = read.get(0);
            aas = read.get(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        read = decode(covs);
        qsa = read.get(0);
        qsb = read.get(1);
        qsc = read.get(2);
        as = read.get(3);
        //
    }
    //
    public void write(List<String> covs, List<String> aas) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("beeb.txt");
        File file = new File(filePath.getFile());
        BufferedWriter thingThatWrites = new BufferedWriter(new FileWriter(file));
        BufferedWriter backup = new BufferedWriter(new FileWriter("C:\\Users\\Eric\\Documents\\CodingTest\\Backups\\beebB.txt"));
        covs.forEach(string ->{
            try {
                thingThatWrites.write(string);
                thingThatWrites.newLine();
                backup.write(string);
                backup.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thingThatWrites.flush();
        thingThatWrites.close();
        backup.flush();
        backup.close();
        //
        filePath = classLoader.getResource("baas.text");
        BufferedWriter thingThatWrites1 = new BufferedWriter(new FileWriter(filePath.getFile()));
        aas.forEach(string ->{
            try {
                thingThatWrites1.write(string);
                thingThatWrites1.newLine();
            }   catch (IOException e){
                e.printStackTrace();
            }
        });
        //
        System.out.println("File Saved!");
        thingThatWrites1.flush();
        thingThatWrites1.close();
    }
    //
    public List<List<String>> read() throws IOException {
        List<List<String>> combined = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        URL filePath = classLoader.getResource("covs.txt");
        File file = new File(filePath.getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> covs = new ArrayList<>();
        String line = br.readLine();
        while(line != null){
            covs.add(line);
            line = br.readLine();
        }
        combined.add(covs);
        //
        br = new BufferedReader(new FileReader("C:\\Users\\Eric\\Documents\\CodingTest\\aas.txt"));
        List<String> aas = new ArrayList<>();
        line = br.readLine();
        while(line != null){
            aas.add(line);
            line = br.readLine();
        }
        combined.add(aas);
        //
        return combined;
    }
    //
    public static List<List<String>> decode(List<String> covs){
        List<String> qsa = new ArrayList<>();
        List<String> qsb = new ArrayList<>();
        List<String> qsc = new ArrayList<>();
        List<String> as = new ArrayList<>();
        //
        for (double n = 0; n < covs.size(); n++){
            if (n/4 == Math.floor(n/4)){
                qsa.add(covs.get((int)n));
            }else if (n/4 == Math.floor(n/4) + .25){
                qsb.add(covs.get((int)n));
            }else if (n/4 == Math.floor(n/4) + .5){
                qsc.add(covs.get((int)n));
            }else{
                as.add(covs.get((int)n));
            }
        }
        //
        //<editor-fold desc="Return">
        List<List<String>> combined = new ArrayList<List<String>>();
        combined.add(qsa);
        combined.add(qsb);
        combined.add(qsc);
        combined.add(as);
        //
        return combined;
        //</editor-fold>
    }
}
