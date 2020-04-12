package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File file = new File("src/main/resources/" + path);
        long count = 0;
        File[] files = file.listFiles();
        for (File tempFile : files != null ? files : new File[0]) {     //Если listFiles вернул null - подменяем на пустой массив
            if (tempFile.isDirectory()) {
                count += countFilesInDirectory(path + File.separator + tempFile.getName());
            } else {
                count++;

            }
        }
        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File file = new File("src/main/resources/" + path);
        long count = 0;
        if(file.isDirectory()){
            count++;
        }
        File[] files = file.listFiles();
        for (File tempFile : files != null ? files : new File[0]) {     //Если listFiles вернул null - подменяем на пустой массив
            if (tempFile.isDirectory()) {
                count += countDirsInDirectory(path + File.separator + tempFile.getName());
            }
        }
        return count;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        Path originalPath = Paths.get(from);
        Path copied = Paths.get(to);
        try {
            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File newPath = new File(path);
        File newFile = new File(path + "/" + name);
        try {
            if (newPath.mkdir()) {
                return newFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        /* т.к. возвращаем одну строку, а не массив строк, то и считываю далее из файла только одну строку */
        try (BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/" + fileName))) {
            return bufReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
