package com.globant.automation.trainings.texts;

import com.globant.automation.trainings.languages.Language;
import com.globant.automation.trainings.logging.Logging;
import com.globant.automation.trainings.tests.Context;
import com.globant.automation.trainings.tests.TestContext;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.globant.automation.trainings.logging.Reporter.REPORTER;
import static java.lang.String.format;
import static java.lang.Thread.currentThread;

public enum Dictionary implements Logging {

    DICTIONARY("Languages.xlsx");

    private static final int KEY_COLUMN = 0;
    private static final int VALUE_COLUMN = 1;
    private static final Map<Language, Map<String, String>> CACHE = new ConcurrentHashMap<>();
    private final XSSFWorkbook workbook;

    Dictionary(String fileName) {
        XSSFWorkbook tempWorkbook;
        try {
            tempWorkbook = new XSSFWorkbook(currentThread().getContextClassLoader().getResourceAsStream(fileName));
        } catch (Exception e) {
            REPORTER.error("Could not load %s to build dictionary! Text matching will suffer...", fileName);
            getLogger().error(e.getLocalizedMessage(), e);
            tempWorkbook = new XSSFWorkbook();
        }
        workbook = tempWorkbook;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                workbook.close();
            } catch (IOException e) {
                getLogger().error(e.getLocalizedMessage(), e);
            }
        }));
    }

    public String get(String key) {
        Context ctx = TestContext.get();
        Language language = ctx.getLanguage();
        return get(key, language);
    }

    public String get(String key, Language language) {

        getLogger().debug(format("Looking up %s in dictionary...", key));

        // Attempt to retrieve from cache first...
        if (CACHE.get(language).containsKey(key)) {
            return CACHE.get(language).get(key);
        } else {
            getLogger().debug(format("Cache miss for key %s", key));
        }

        String translation = lookUpTranslation(key, language);
        if (!translation.isEmpty()) {
            // Put in CACHE
            Map<String, String> languageCache = CACHE.computeIfAbsent(language, k -> new ConcurrentHashMap<>());
            languageCache.put(key, translation);
            return translation;
        }

        return "";
    }

    private String lookUpTranslation(String key, Language language) {
        Sheet workingSheet = workbook.getSheetAt(language.ordinal());

        for (Row currentRow : workingSheet) {

            String rowKey = currentRow.getCell(KEY_COLUMN).getStringCellValue();

            boolean wrongRow = "key".equals(rowKey) || !rowKey.equals(key);

            if (wrongRow) continue;

            Cell cell = currentRow.getCell(VALUE_COLUMN);
            if (cell == null) {
                break;
            }
            String translation;
            try {
                translation = cell.getStringCellValue();
            } catch (IllegalStateException ise) {
                getLogger().debug("Could not retrieve cell value as string, reattempting as Numeric value ", ise);
                translation = String.valueOf(cell.getNumericCellValue());
            }

            // Seems like POI appends an extra \ retrieves if cell contains one already...fixing it
            translation = translation.replace("\\\\", "\\").trim();

            return translation;
        }
        return "";
    }

}