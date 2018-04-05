package bashfulj.annotations;

public @interface Bash {
	public String command() default ":";
}
