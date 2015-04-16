package code_boss.compiling;

import java.io.File;

public class CppCompiler extends CodeCompiler {
    public static final String MAIN = "main";
    private static final String CPP_COMPILE = "g++";
    private static final String CPP_EXT = "cpp";
    private static final String CPP_COMPILED_FILE = "a.out";

    public CppCompiler(ICompilationResultListener listener) {
        super(listener);
    }

    @Override
    protected int getLanguageIndex() {
        return 1;
    }

    @Override
    protected String getDefaultFileName() {
        return "main";
    }

    @Override
    protected String getFileExtension() {
        return ".cpp";
    }

    @Override
    protected File getCompilationDirectory(String filePath) {
        String solutionPath = filePath.substring(0, filePath.indexOf(MAIN));
        return new File(solutionPath);
    }

    @Override
    protected String getCompilationCommand() {
        return CPP_COMPILE;
    }

    @Override
    protected String getCompilationArgs() {
        return String.format("%s.%s", MAIN, CPP_EXT);
    }

    @Override
    protected boolean deleteCompiledFile(String dirName) {
        String filePath = String.format("%s%s", dirName, CPP_COMPILED_FILE);
        File compiledFile = new File(filePath);
        return compiledFile.delete();
    }
}
