class Result {

    /*
     * Complete the 'predictMissingHumidity' function below.
     *
     * The function is expected to return a FLOAT_ARRAY.
     * The function accepts following parameters:
     *  1. STRING startDate
     *  2. STRING endDate
     *  3. STRING_ARRAY knownTimestamps
     *  4. FLOAT_ARRAY humidity
     *  5. STRING_ARRAY timestamps
     */

    public static List<Float> predictMissingHumidity(String startDate, String endDate, List<String> knownTimestamps, List<Float> humidity, List<String> timestamps) {
    // Write your code here
        double[] sourceX = new double[knownTimestamps.size()];
        double[] sourceY = new double[humidity.size()];
        double[] newX = new double[timestamps.size()];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:00");
        
        try {
        for(int i = 0; i < knownTimestamps.size(); i++) {
            sourceX[i] = formatter.parse(knownTimestamps.get(i)).getTime();
        }
        
        for(int i = 0; i < timestamps.size(); i++) {
            newX[i] = formatter.parse(timestamps.get(i)).getTime();
        }
        } catch (ParseException e) {}
        
        for(int i = 0; i < humidity.size(); i++) {
            sourceY[i] = humidity.get(i);
        }
        
        int N = knownTimestamps.size();
        
        int Nx = N - 1;
            
        double[] dx = new double[Nx];

        double[] b = new double[N];
        double[] alfa = new double[N];
        double[] beta = new double[N];
        double[] gama = new double[N];

        double[][] coefs = new double[4][];
        for (int i = 0; i < 4; i++)
            coefs[i] = new double[Nx];

        for (int i = 0; i + 1 <= Nx; i++)
        {
            dx[i] = sourceX[i + 1] - sourceX[i];
            if (dx[i] == 0.0)
                return null;
        }

        for (int i = 1; i + 1 <= Nx; i++)
        {
            b[i] = 3.0 * (dx[i] * ((sourceY[i] - sourceY[i - 1]) / dx[i - 1]) + dx[i - 1] * ((sourceY[i + 1] - sourceY[i]) / dx[i]));
        }

        b[0] = ((dx[0] + 2.0 * (sourceX[2] - sourceX[0])) * dx[1] * ((sourceY[1] - sourceY[0]) / dx[0]) +
                    Math.pow(dx[0], 2.0) * ((sourceY[2] - sourceY[1]) / dx[1])) / (sourceX[2] - sourceX[0]);

        b[N - 1] = (Math.pow(dx[Nx - 1], 2.0) * ((sourceY[N - 2] - sourceY[N - 3]) / dx[Nx - 2]) + (2.0 * (sourceX[N - 1] - sourceX[N - 3])
            + dx[Nx - 1]) * dx[Nx - 2] * ((sourceY[N - 1] - sourceY[N - 2]) / dx[Nx - 1])) / (sourceX[N - 1] - sourceX[N - 3]);

        beta[0] = dx[1];
        gama[0] = sourceX[2] - sourceX[0];
        beta[N - 1] = dx[Nx - 1];
        alfa[N - 1] = (sourceX[N - 1] - sourceX[N - 3]);
        for (int i = 1; i < N - 1; i++)
        {
            beta[i] = 2.0 * (dx[i] + dx[i - 1]);
            gama[i] = dx[i];
            alfa[i] = dx[i - 1];
        }
        double c = 0.0;
        for (int i = 0; i < N - 1; i++)
        {
            c = beta[i];
            b[i] /= c;
            beta[i] /= c;
            gama[i] /= c;

            c = alfa[i + 1];
            b[i + 1] -= c * b[i];
            alfa[i + 1] -= c * beta[i];
            beta[i + 1] -= c * gama[i];
        }

        b[N - 1] /= beta[N - 1];
        beta[N - 1] = 1.0;
        for (int i = N - 2; i >= 0; i--)
        {
            c = gama[i];
            b[i] -= c * b[i + 1];
            gama[i] -= c * beta[i];
        }

        for (int i = 0; i < Nx; i++)
        {
            double dzzdx = (sourceY[i + 1] - sourceY[i]) / Math.pow(dx[i], 2.0) - b[i] / dx[i];
            double dzdxdx = b[i + 1] / dx[i] - (sourceY[i + 1] - sourceY[i]) / Math.pow(dx[i], 2.0);
            coefs[0][i] = (dzdxdx - dzzdx) / dx[i];
            coefs[1][i] = (2.0 * dzzdx - dzdxdx);
            coefs[2][i] = b[i];
            coefs[3][i] = sourceY[i];
        }

        double[] newY = new double[newX.length];
        int j = 0;
        for (int i = 0; i < N - 1; i++)
        {
            double h = 0.0;
            if (j >= newX.length)
                break;
            while (newX[j] < sourceX[i + 1])
            {
                h = newX[j] - sourceX[i];
                newY[j] = coefs[3][i] + h * (coefs[2][i] + h * (coefs[1][i] + h * coefs[0][i] / 3.0) / 2.0);
                j++;
                if (j >= newX.length)
                    break;
            }
            if (j >= newX.length)
                break;
        }

        newY[newY.length - 1] = sourceY[N - 1];
        
        
        ArrayList<Float> ans = new ArrayList<>();
        for(int i = 0; i < newY.length; i++) {
            ans.add((float)newY[i]);
        }
        
        return ans;
    }

}