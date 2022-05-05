// update the class
class MyException extends Exception {

    public MyException(String message) {
        super(message);
    }

}

//do not change the code
class Main {
    public static void main(String[] args) {
        MyException myException = new MyException("This is a custom exception");
        System.out.println(myException.getMessage());
        Class<?> superClass = myException.getClass().getSuperclass();
        System.out.println(superClass.equals(Exception.class));
    }
}