package code_boss.compiling;

import code_boss.model.CompilationResult;
import code_boss.model.UserSolution;

import java.io.*;

public abstract class CodeCompiler {
    private static final String CODE_DIR = "code";

    private ICompilationResultListener listener;

    public CodeCompiler(ICompilationResultListener listener) {
        this.listener = listener;
    }

    public void CompileSolution(UserSolution solution) {
        String solutionFilePath = createSolutionFile(solution);

        // respond with server error if solution file couldn't be open
        if(solutionFilePath == null) {
            CompilationResult result = new CompilationResult("Unable to create solution file");
            listener.notifyResult(result);
            return;
        }

        // if file create then compile it
        compileSolution(solution, solutionFilePath);
    }

    public void cleanUp(String filePath) {
        String msg;

        // delete source file
        File file = new File(filePath);
        if (file.exists()) {
            msg = file.delete() ?
                    String.format("Deleted source file at %s", filePath) :
                    String.format("Unable to delete source file at %s", filePath);
            System.out.println(msg);
        }

        // delete compiled file
        String dirPath = filePath.substring(0, filePath.indexOf(getDefaultFileName()));
        msg = deleteCompiledFile(dirPath) ?
                String.format("Deleted compiled file at %s", dirPath) :
                String.format("Unable to delete compiled file at %s", dirPath);
        System.out.println(msg);

        // sleep so that the directory is empty when we try to delete it
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // delete problem directory
        File dir = new File(dirPath);
        if (dir.exists()) {
            msg = dir.delete() ?
                    String.format("Deleted directory %s", dirPath) :
                    String.format("Unable to delete directory directory %s", dirPath);
            System.out.println(msg);
        }
    }

    protected abstract int getLanguageIndex();

    protected abstract String getDefaultFileName();

    protected abstract String getFileExtension();

    protected abstract File getCompilationDirectory(String filePath);

    protected abstract String getCompilationCommand();

    protected abstract String getCompilationArgs();

    protected abstract boolean deleteCompiledFile(String dirName);

    private String createSolutionFile(UserSolution solution) {
        StringBuilder sb = new StringBuilder();
        createDirIfNeed(CODE_DIR);
        sb.append(CODE_DIR);
        sb.append("/");
        createDirIfNeed(String.format("%s%s", sb.toString(), solution.getUserId()));
        sb.append(solution.getUserId());
        sb.append("/");
        createDirIfNeed(String.format("%s%s", sb.toString(), solution.getProblemId()));
        sb.append(solution.getProblemId());
        sb.append("/");
        sb.append(getDefaultFileName());
        sb.append(getFileExtension());

        return createFile(solution, sb.toString());
    }

    private void createDirIfNeed(String path) {
        File file = new File(path);
        if (!file.exists()) {
            String msg = file.mkdir() ?
                String.format("Successfully created directory %s", path) :
                String.format("Unable to create directory %s", path);
            System.out.println(msg);
        }
    }

    private String createFile(UserSolution solution, String solutionPath) {
        try {
             File file = new File(solutionPath);
            if(!file.exists()) {
                String msg = file.createNewFile() ?
                        String.format("Successfully created solution file %s", solutionPath) :
                        String.format("Unable to create solution file %s", solutionPath);
                System.out.println(msg);
            }

            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(solution.getCode());
            output.close();

            return file.getPath();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return null;
    }

    private void compileSolution(final UserSolution solution, final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProcessBuilder pb = new ProcessBuilder(getCompilationCommand(), getCompilationArgs());
                pb.directory(getCompilationDirectory(filePath));
                pb.redirectErrorStream(true);
                try {
                    System.out.println(String.format("Executing %s %s to compile %s", getCompilationCommand(), getCompilationArgs(), filePath));
                    Process compilingProcess = pb.start();
                    compilingProcess.waitFor();

                    CompilationResult result = compilingProcess.exitValue() == 0 ?
                            new CompilationResult(getLanguageIndex(), solution, filePath, true) :
                            new CompilationResult(getLanguageIndex(), solution, filePath, false, getOutput(compilingProcess.getInputStream()));

                    listener.notifyResult(result);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getOutput(InputStream programOutput) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(programOutput, "UTF-8"));

            String line;
            do {
                line = bufferedReader.readLine();
                sb.append(line != null ? line : "");
                sb.append("\n");
            } while (line != null);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
