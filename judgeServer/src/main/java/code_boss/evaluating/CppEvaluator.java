package code_boss.evaluating;

import code_boss.compiling.CppCompiler;

import java.io.File;

public class CppEvaluator extends CodeEvaluator {
    private static final String CPP_EXEC = "./a.out";

    public CppEvaluator(ICodeEvaluationListener listener) {
        super(listener);
    }

    @Override
    protected String getExecutionCommand() {
        return CPP_EXEC;
    }

    @Override
    protected String getExecutionArgs() {
        return "";
    }

    @Override
    protected File getExecutionDirectory(String compiledFilePath) {
        String solutionPath = compiledFilePath.substring(0, compiledFilePath.indexOf(CppCompiler.MAIN));
        return new File(solutionPath);
    }
}
