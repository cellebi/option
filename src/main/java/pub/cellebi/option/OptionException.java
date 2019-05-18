package pub.cellebi.option;

public class OptionException extends RuntimeException {



    public OptionException() {
        super();
    }

    public OptionException(String msg) {
        super(msg);
    }

    public OptionException(ErrorMsg errorMsg) {
    }

    public OptionException(String msg, Throwable e) {
        super(msg, e);
    }
}
