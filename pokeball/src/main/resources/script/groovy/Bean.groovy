package script.groovy

/**
 * @author theo* @date 19-8-30
 */
class Bean {

    public String bean(String who) {
        if (who == null)
            return "that's my bean"
        return "that's my " + who
    }

}
