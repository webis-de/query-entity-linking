package de.webis.query.entitylinking.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.webis.query.entitylinking.datastructures.Entity;
import de.webis.query.entitylinking.datastructures.Query;
import de.webis.query.entitylinking.ExplicitEntityLinker;
import de.webis.query.entitylinking.strategies.AllNGrams;
import picocli.CommandLine;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author marcel.gohsen@uni-weimar.de
 */
@CommandLine.Command(name = "entitylink", mixinStandardHelpOptions = true)
public class App implements Callable<Void> {
    @CommandLine.Option(names = "--input", required = true)
    private Path inputDir;

    @CommandLine.Option(names = "--output", required = true)
    private Path outputDir;

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public List<ObjectNode> parseQueryFile(){
        final List<ObjectNode> queries = new LinkedList<>();
        final File file = Paths.get(inputDir.toString(), "queries.jsonl").toFile();

        try(final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null){
                queries.add(OBJECT_MAPPER.readValue(line, ObjectNode.class));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return queries;
    }

    @Override
    public Void call() throws Exception {
        final ExplicitEntityLinker explicitEntityLinker = ExplicitEntityLinker.getInstance(new AllNGrams());
        final List<ObjectNode> queries = parseQueryFile();

        for(final ObjectNode jsonNode: queries){
            final Set<Entity> entities =
                    explicitEntityLinker.annotate(new Query(jsonNode.get("query").asText()));
            System.out.println(jsonNode.get("query"));
            entities.forEach(System.out::println);
            jsonNode.putPOJO("entities", entities);
        }

        writeOutput(queries);
        return null;
    }

    public void writeOutput(final List<ObjectNode> queries){
        final File file = Paths.get(outputDir.toString(), "queries.jsonl").toFile();
        System.out.println(file);

        try(final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            for(final ObjectNode query: queries){
                bufferedWriter.write(OBJECT_MAPPER.writeValueAsString(query));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        new CommandLine(new App()).execute(args);
    }
}
