package code_boss.compiling;

import java.io.File;

public class JavaCompiler extends CodeCompiler {
    public static final String MAIN = "Main";
    private static final String COMPILED_EXT = ".class";
    private static final String JAVA_EXT = ".java";
    private static final String JAVA_COMPILE = "javac";


    public JavaCompiler(ICompilationResultListener listener) {
        super(listener);
    }

    @Override
    protected int getLanguageIndex() {
        return 0;
    }

    @Override
    protected String getDefaultFileName() {
        return MAIN;
    }

    @Override
    protected String getFileExtension() {
        return JAVA_EXT;
    }

    @Override
    protected File getCompilationDirectory(String filePath) {
        String solutionPath = filePath.substring(0, filePath.indexOf(MAIN));
        return new File(solutionPath);
    }

    @Override
    protected String getCompilationCommand() {
        return JAVA_COMPILE;
    }

    @Override
    protected String getCompilationArgs() {
        return MAIN;
    }

    @Override
    protected boolean deleteCompiledFile(String dirName) {
        String filePath = String.format("%s%s%s", dirName, MAIN, COMPILED_EXT);
        File compiledFile = new File(filePath);
        return compiledFile.delete();
    }
}
