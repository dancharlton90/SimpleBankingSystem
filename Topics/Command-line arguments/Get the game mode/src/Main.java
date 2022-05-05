class Problem {
    public static void main(String[] args) {
        String gameMode = "default";
        for (int i = 0; i < args.length; i += 2) {
            if ("mode".equals(args[i])) {
                gameMode = args[i + 1];
            }
        }
        System.out.println(gameMode);
    }
}