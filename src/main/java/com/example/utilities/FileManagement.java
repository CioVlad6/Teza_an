package com.example.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManagement {
  final static String exeFileListFilePath = "src/main/java/com/example/utilities/exeFileList.txt";
  
  public static void updateExeFileList(ExeFile exeFile) {
    StringBuilder text = new StringBuilder();
    File exeFileListFile = new File(exeFileListFilePath);
    
    try {
      Scanner in = new Scanner(exeFileListFile);
      while (in.hasNextLine()) {
        text.append(in.nextLine()).append('\n');
      }
      text.append(exeFile.name()).append('|').append(exeFile.url());
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    try {
      PrintWriter out = new PrintWriter(exeFileListFile, StandardCharsets.UTF_8);
      out.print(text);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static ArrayList<ExeFile> loadExeFilesList(){
    ArrayList<ExeFile> exeFileList = new ArrayList<>();
    try {
      File exeFileListFile = new File(exeFileListFilePath);
      Scanner in = new Scanner(exeFileListFile);
      int i = 0;
      while(in.hasNextLine()){
        String[] str = in.nextLine().split("\\|");
        exeFileList.add(new ExeFile(i++,str[0],str[1]));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return exeFileList;
  }
}
