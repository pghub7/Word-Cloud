import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Instrumentality {
    private static final Statement initLogger = StaticJavaParser.parseStatement("logger.Logger.initializeLogger();");
    private static Statement logDump = null;
    private static final Statement logMethod = StaticJavaParser.parseStatement("logger.Logger.getInstance().logMethodCall();");
    private static final Statement visualizationMethod = StaticJavaParser.parseStatement("Visualization.WordCloudLogic.startWordCloud();");

    public static void main(String[] args) throws Exception {
        File targetProjectDir = new File(args[0]);
        if (args.length > 1) {
            File outputDir = new File(args[1]);
            //If an input with Windows-style separators is used, reformat them so that they can be parsed.
            String formatPath = outputDir.getAbsolutePath().replace('\\', '/');
            logDump = StaticJavaParser.parseStatement("logger.Logger.getInstance().dumpLogs(\"" + formatPath + "\");");
        }

        List<String> src_files = listNamesOfFiles(targetProjectDir)
                .stream()
                .filter(s -> s.endsWith(".java"))
                .collect(Collectors.toList());

        for (String filePath : src_files) {
            File sourceFile = new File(filePath);
            CompilationUnit cu = StaticJavaParser.parse(sourceFile);
            MethodDeclarationModifier mmd = new MethodDeclarationModifier();
            mmd.visit(cu, null);
            MainModifier mm = new MainModifier();
            mm.visit(cu, null);

            System.out.println(cu);
            FileWriter fileWriter = new FileWriter(sourceFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(cu);
            printWriter.close();
        }
    }

    private static class MainModifier extends ModifierVisitor<Void> {
        @Override
        public MethodDeclaration visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
            if (!isPublicStaticVoidMain(md)) {
                return md;
            }
            md.getBody().ifPresent(body -> {
                body.addStatement(0, initLogger);
                if (logDump != null)
                    body.addStatement(logDump);
                body.addStatement(visualizationMethod);
            });
            return md;
        }

        public static Boolean isPublicStaticVoidMain(MethodDeclaration md) {
            return md.getNameAsString().equals("main") && md.getType().toString().equals("void") &&
                    md.hasModifier(Modifier.Keyword.STATIC) && md.hasModifier(Modifier.Keyword.PUBLIC) &&
                    md.getParameters().size() != 0 && md.getParameter(0).toString().equals("String[] args");
        }
    }

    private static class MethodDeclarationModifier extends ModifierVisitor<Void> {
        @Override
        public MethodDeclaration visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
            md.getBody().ifPresent(body -> body.addStatement(0, logMethod));
            return md;
        }
    }

    private static List<String> listNamesOfFiles(File folder) {
        List<String> strings = new ArrayList<>();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) {
                strings.addAll(listNamesOfFiles(file));
            } else {
                strings.add(file.getAbsolutePath());
            }
        }
        return strings;
    }
}
