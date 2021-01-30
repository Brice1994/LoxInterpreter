package com.interpreters.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws Exception {
        String userDir = System.getProperty("user.dir");
        String outputDir = userDir + "\\src\\com\\interpreters\\lox";
        defineAst(outputDir, "Expr", Arrays.asList(
                "Binary   : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal  : Object value",
                "Unary    : Token operator, Expr right"
        ));
    }
    private static void defineAst(
            String outputDir,
            String baseName,
            List<String> types
    ) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);
        writer.println("package com.interpreters.lox;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + " {");
        types.forEach(type -> {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        });
        writer.println("}");
        writer.close();

    }
    private static void defineType(
            PrintWriter writer,
            String baseName,
            String className,
            String fieldList
    ){
        writer.println(" static class " + className + " extends " + baseName + "{");
        writer.println("     " + className + "(" + fieldList + ") {");
        List<String> fields = Arrays.asList(fieldList.split(", "));
        fields.forEach(field -> {
            String name = field.split(" ")[1];
            writer.println("     this." + name + " = " + name + ";");
        });
        writer.println("     }");
        writer.println();
        fields.forEach(field -> {
            writer.println("     final " + field + ";");
        });
        writer.println("   }");
    }
}
