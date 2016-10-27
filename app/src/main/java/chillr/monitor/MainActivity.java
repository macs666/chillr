package chillr.monitor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{
    TextView sm[][] = new TextView[4][4];

    private Timer timer;
    private TimerTask timerTask;
    static String[] srv = {"chillr-netmagic-cloud-bangalore-api","chillr-netmagic-cloud-bangalore-api-2","chillr-netmagic-cloud-bangalore-api-3","chillr-netmagic-cloud-bangalore-api-factory"};
    static String[] metr = {"system.disk.free","system.mem.free","nginx.net.avg_response","mongodb.metrics.document.returnedps"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{

            MainTask task = (MainTask) new MainTask(1).execute();

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
        /*CardView card1,card2,card3,card4;
        card1 = (CardView) findViewById(R.id.card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Next.class);
                intent.putExtra("server",srv[0]);
                startActivity(intent);
            }
        });
        card2 = (CardView) findViewById(R.id.card2);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Next.class);
                intent.putExtra("server",srv[1]);
                startActivity(intent);
            }
        });
        card3 = (CardView) findViewById(R.id.card3);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Next.class);
                intent.putExtra("server",srv[2]);
                startActivity(intent);
            }
        });
        card4 = (CardView) findViewById(R.id.card4);
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Next.class);
                intent.putExtra("server",srv[3]);
                startActivity(intent);
            }
        });
*/
        try {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    MainTask task = (MainTask) new MainTask(2);
                    task.execute();
                }
            };
            timer.schedule(timerTask, 30000, 30000);
        } catch (IllegalStateException e){
            android.util.Log.i("Damn", "resume error");
        }
    }
    private class MainTask extends AsyncTask<Void, Void,Server[]> {
        ProgressDialog progress;
        int flag = 0;

        MainTask(int flag){
            this.flag = flag;
        }

        @Override
        protected Server[] doInBackground(Void... params) {
            Server[] servers = new Server[4];
            JsonPars data = new JsonPars();
            ServerClient con = new ServerClient();
            Server s , def = new Server("");
            int i;
            String str;
            try {

                for(i=0;i<4;i++){
                    s = new Server(srv[i]);
                    str = con.getServerData(metr[0], srv[i]);
                    if(str!=null) {
                        s = data.serverdetails(str, metr[0], s);
                    }
                    else s=def;
                    str = con.getServerData(metr[1], srv[i]);
                    if(str!=null) {
                        s = data.serverdetails(str, metr[1], s);
                    }
                    str = con.getServerData(metr[2], srv[i]);
                    if(str!=null) {
                        try {
                            s = data.serverdetails(str, metr[2], s);
                        } catch (JSONException e) {
                            s.setAvgResp(0);
                        }
                    }
                    else s=def;
                    str = con.getServerData(metr[3], srv[i]);
                    if(str!=null) {
                        s = data.serverdetails(str, metr[3], s);
                    }
                    else s=def;
                    servers[i]=s;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return servers;
        }
        @Override
        protected void onPreExecute(){
            if (flag == 1.) {
                progress = new ProgressDialog(MainActivity.this);
                progress.setTitle("Loading");
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
            }
        }

        @Override
        protected void onPostExecute(Server[] servers) {
            Server srv;
            if(servers == null){
                Toast.makeText(MainActivity.this,"Nothing to show",Toast.LENGTH_LONG).show();
            }
            else {
                sm[0][0] = (TextView) findViewById(R.id.metr1_1);
                sm[0][1] = (TextView) findViewById(R.id.metr1_2);
                sm[0][2] = (TextView) findViewById(R.id.metr1_3);
                sm[0][3] = (TextView) findViewById(R.id.metr1_4);
                sm[1][0] = (TextView) findViewById(R.id.metr2_1);
                sm[1][1] = (TextView) findViewById(R.id.metr2_2);
                sm[1][2] = (TextView) findViewById(R.id.metr2_3);
                sm[1][3] = (TextView) findViewById(R.id.metr2_4);
                sm[2][0] = (TextView) findViewById(R.id.metr3_1);
                sm[2][1] = (TextView) findViewById(R.id.metr3_2);
                sm[2][2] = (TextView) findViewById(R.id.metr3_3);
                sm[2][3] = (TextView) findViewById(R.id.metr3_4);
                sm[3][0] = (TextView) findViewById(R.id.metr4_1);
                sm[3][1] = (TextView) findViewById(R.id.metr4_2);
                sm[3][2] = (TextView) findViewById(R.id.metr4_3);
                sm[3][3] = (TextView) findViewById(R.id.metr4_4);
                NumberFormat myformatter = new DecimalFormat("#.###");
                int green = ContextCompat.getColor(MainActivity.this, R.color.green);
                int yellow = ContextCompat.getColor(MainActivity.this, R.color.yellow);
                int red = ContextCompat.getColor(MainActivity.this, R.color.red);

                try {
                    for (int i = 0; i < 4; i++) {
                        srv = servers[i];
                        if (servers[i].getFlag().contentEquals("")) {
                            sm[i][0].setText(myformatter.format(srv.getAvgDisk()));
                            if (srv.getAvgDisk() >= 6.0E10)
                                sm[i][0].setTextColor(red);
                            else if (srv.getAvgDisk() >= 2.0E10 && srv.getAvgDisk() < 6.0E10)
                                sm[i][0].setTextColor(yellow);
                            else
                                sm[i][0].setTextColor(green);
                            sm[i][1].setText(myformatter.format(srv.getAvgMem()));
                            if (srv.getAvgMem() >= 7.0E9)
                                sm[i][1].setTextColor(red);
                            else if (srv.getAvgMem() >= 6.0E9 && srv.getAvgMem() < 7.0E9)
                                sm[i][1].setTextColor(yellow);
                            else
                                sm[i][1].setTextColor(green);
                            sm[i][2].setText(myformatter.format(srv.getAvgResp()));
                            if (srv.getAvgResp() >= 5)
                                sm[i][2].setTextColor(red);
                            else if (srv.getAvgResp() >= 1 && srv.getAvgResp() < 5)
                                sm[i][2].setTextColor(yellow);
                            else
                                sm[i][2].setTextColor(green);
                            sm[i][3].setText(myformatter.format(srv.getAvgRetn()));
                            if (srv.getAvgRetn() >= 1000)
                                sm[i][3].setTextColor(red);
                            else if (srv.getAvgRetn() >= 500 && srv.getAvgRetn() < 1000)
                                sm[i][3].setTextColor(yellow);
                            else
                                sm[i][3].setTextColor(green);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,servers[i].getFlag(),Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(flag == 1) {
                progress.cancel();
            }
        }
    }

}