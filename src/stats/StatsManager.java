package stats;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.*;

public class StatsManager {
    private static final String FILE_PATH = "stat/stat.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static StatsData loadStats() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, StatsData.class);
        } catch (IOException e) {
            return new StatsData();
        }
    }

    public static void saveStats(StatsData data) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

