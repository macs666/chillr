package chillr.monitor;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Next extends AppCompatActivity {
    GraphView g1,g2,g3,g4;
    private Timer timer;
    private TimerTask timerTask;
   static String[] metr = {"system.disk.free","system.mem.free","nginx.net.avg_response","mongodb.metrics.document.returnedps"};
    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        Bundle bundle = getIntent().getExtras();
        String serv = bundle.getString("server");
        TextView head = (TextView) findViewById(R.id.serHead);
        head.setText(serv);
        g1 = (GraphView) findViewById(R.id.graph1);
        g2 = (GraphView) findViewById(R.id.graph2);
        g3 = (GraphView) findViewById(R.id.graph3);
        g4 = (GraphView) findViewById(R.id.graph4);
        g1.addSeries(series1);
        g2.addSeries(series2);
        g3.addSeries(series3);
        g4.addSeries(series4);
        try{

            MainTask task = (MainTask) new MainTask().execute(serv);

        }catch (Exception e){
            Log.i("shit", "happens");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    public void onResume(){
        super.onResume();
        try {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    MainTask task = (MainTask) new MainTask().execute();
                }
            };
            timer.schedule(timerTask, 10000, 10000);
        } catch (IllegalStateException e){
            android.util.Log.i("Damn", "resume error");
        }

    }


    private class MainTask extends AsyncTask<String, Object, Server> {
        ProgressDialog progress;

        @Override
        protected Server doInBackground(String... srv) {

            JsonPars data = new JsonPars();
            ServerClient con = new ServerClient();
            Server s ;
            s = new Server(srv[0]);
            String str;
            try {
                    str = con.getServerData(metr[0], srv[0]);
                    s = data.serverdetails(str, metr[0],s);
                    str = con.getServerData(metr[1], srv[0]);
                    s = data.serverdetails(str, metr[1],s);
                    str = con.getServerData(metr[3], srv[0]);
                    s = data.serverdetails(str, metr[3],s);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }

            try {
                str = con.getServerData(metr[2], srv[0]);
                s = data.serverdetails(str, metr[2],s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return s;
        }
        @Override
        protected void onPreExecute(){
            progress = new ProgressDialog(Next.this);
            progress.setTitle("Loading");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        }

        @Override
        protected void onPostExecute(Server server) {
            if (server == null) {
                Toast.makeText(Next.this, "Nothing to show", Toast.LENGTH_LONG).show();
            } else {
                DataPoint[] newSeries = server.getDisk();
                series1.resetData(newSeries);
                newSeries = server.getMem();
                series2.resetData(newSeries);
                newSeries = server.getResp();
                series3.resetData(newSeries);
                newSeries = server.getRetn();
                series4.resetData(newSeries);

            }
            progress.cancel();
        }
    }
}
