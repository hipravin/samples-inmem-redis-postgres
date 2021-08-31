package com.hipravin.samples;

import com.hipravin.samples.api.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DataGeneratorService {
    @Value("classpath:names/firstnames.txt")
    Resource firstNamesFile;
    @Value("classpath:names/lastnames.txt")
    Resource lastNamesFile;

    public Stream<EmployeeDto> randomEmployees() {
        try {
            List<String> firstNames = readLines(firstNamesFile);
            List<String> lastNames = readLines(lastNamesFile);
            if (firstNames.isEmpty() || lastNames.isEmpty()) {
                throw new IllegalStateException("No name data");
            }

            Random random = new Random(0);
            return Stream.generate(() -> randomEmployee(random, firstNames, lastNames));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static EmployeeDto randomEmployee(
            Random random, List<String> firstNames, List<String> lastNames) {
        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));

        String email = firstName + "." + lastName + "@mail.com";
        String emailLowerCase = email.toLowerCase();

        return new EmployeeDto(emailLowerCase, firstName, lastName);
    }

    private static List<String> readLines(Resource resource) throws IOException {
        try (Stream<String> lines = Files.lines(resource.getFile().toPath())) {
            return lines.collect(Collectors.toList());
        }
    }


}
