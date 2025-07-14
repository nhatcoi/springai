package com.nhatdev.springai.utils;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;

import java.io.IOException;
import java.util.List;

public class LanguageDetect {

    public static String detectLanguage(String text) throws IOException {

        List<LanguageProfile> languageProfiles = new LanguageProfileReader().readAllBuiltIn();

        LanguageDetector detector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                .withProfiles(languageProfiles)
                .build();
        Optional<LdLocale> language = detector.detect(text);
        if (language.isPresent()) {
            System.out.println("Detected language: " + language.get().getLanguage());
        } else {
            System.out.println("No language detected");
        }
        return language.isPresent() ? language.get().getLanguage() : "unknown";
    }

}
