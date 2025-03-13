package exceptions.channel;

import java.io.IOException;

public class Program {

    /* default */ ConstantProvider provider = new ConstantProvider();

    public void run(int input) {
        try {
            double result = calculate(input);
            String formatted = format(String.valueOf(result));
            present(formatted);
        } catch (MissingConstantException e) {
            present(formatError("Constant is missing"));
        } catch (CorruptConfigurationException e) {
            present(formatError("Configuration file is corrupt"));
        } catch (IOException e) {
            present(formatError(e.getMessage()));
        }
    }

    private double calculate(int input) throws MissingConstantException, CorruptConfigurationException, IOException {
        return (input + 42) * provider.getMultiplier2();
    }

    private String format(String data) {
        return "### Result is %s ###".formatted(data);
    }

    private String formatError(String message) {
        return "### Error: %s ###".formatted(message);
    }

    private void present(String data) {
        System.out.println(data);
    }
}