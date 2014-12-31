package de.esailors.apps.fileencryption;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by fbaue on 12/29/14.
 */
public class ConsoleStarter {
    public static void main(String[] args) throws Exception {
        new ConsoleStarter().run(args);
    }

    public void run(String[] args) throws Exception{
        if (args.length != 3) {
            printHelp();
        } else {

            String fileNameIn = args[0];
            String fileNameOut = args[1];
            String password = args[2];

            byte[] fileContent = readFile(fileNameIn);
            byte[] encryptedData = new EncryptionService().encrypt(fileContent, password);
            writeFile(encryptedData, fileNameOut);

            byte[] encryptedFileContent = readFile(fileNameOut);
            byte[] decryptedFileContent = new DecryptionService().decrypt(encryptedFileContent, password);

            if (Arrays.equals(fileContent, decryptedFileContent)) {
                System.out.println("Done.");
            } else {
                throw new RuntimeException("Encryption Failed");
            }
        }
    }

    private void writeFile(byte[] data, String fileName) throws IOException {
        File file = new File(fileName);
        if(file.isFile() && file.exists()){
            file.delete();
        }
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(data);
        }
        file.length();
    }

    private byte[] readFile(String arg) throws IOException {
        File file = new File(arg);
        byte[] bytes = new byte[(int) file.length()];
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            in.read(bytes);
        }
        return bytes;
    }

    private void printHelp() {
        System.out.println("FileEncryptionTool For Android Roomfinder");
        System.out.println("-----------------------------------------");
        System.out.println("first parameter is the name of the input file");
        System.out.println("second paramter is the name of the output file");
        System.out.println("third paramter is the password");
        System.out.println("example: ./tool data.json encrypted_data.json test123");
    }
}
