public class Week4 {
    /**
     * Find max.
     *
     * @param a a
     * @param b b
     *          Return max of $a and $b.
     */
    public static int max2Int(int a, int b) {
        return Math.max(a, b);
    }

    /**
     * Min array.
     *
     * @param arr arr
     *            Return min of array $arr.
     */
    public static int minArray(int[] arr) {
        int min = (int) Double.POSITIVE_INFINITY;
        for (int i : arr) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    /**
     * Calculate BMI.
     *
     * @param weight in kg
     * @param height in m
     *               Return BMI index.
     */
    public static String calculateBMI(double weight, double height) {
        double bmi = Math.round((weight / (height * height)) * 10) / 10.f;
        if (bmi < 18.5) {
            return "Thiếu cân";
        } else if (bmi >= 18.5 && bmi <= 22.9) {
            return "Bình thường";
        } else if (bmi >= 23 && bmi <= 24.9) {
            return "Thừa cân";
        } else {
            return "Béo phì";
        }
    }
}