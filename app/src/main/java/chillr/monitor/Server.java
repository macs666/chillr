package chillr.monitor;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Macs on 10/3/2016.
 */
public class Server {
    String name;
    int srvno;
    String flag;
    DataPoint[] disk,mem,resp,retn;
    float avgMem;
    float avgDisk;
    float avgResp;
    float avgRetn;
    double lastX = 5d;
    double temp;
    Server(String name){
        this.name = name;
        srvno = 0;
        flag = new String("");
        avgMem= 0;
        avgDisk =0;
        avgResp = 0;
        avgRetn = 0;
        disk = new DataPoint[20];
        mem = new DataPoint[20];
        resp = new DataPoint[20];
        retn = new DataPoint[20];

    }
    Server(){
        this.name = "";
        srvno = 0;
        flag = new String("");
        avgMem= 0;
        avgDisk =0;
        avgResp = 0;
        avgRetn = 0;
        disk = new DataPoint[20];
        mem = new DataPoint[20];
        resp = new DataPoint[20];
        retn = new DataPoint[20];

    }

    Server(Server s){
        name = s.getName();
        srvno = s.getSrvno();
        avgMem = s.getAvgMem();
        avgDisk = s.getAvgDisk();
        avgResp = s.getAvgResp();
        avgRetn = s.getAvgRetn();
        flag = s.getFlag();

    }

    public DataPoint[] getDisk() {
        return disk;
    }

    public DataPoint[] getMem() {
        return mem;
    }

    public DataPoint[] getResp() {
        return resp;
    }

    public DataPoint[] getRetn() {
        return retn;
    }

    public void setDisk(Double[] points,int size) {
        DataPoint[] dataPoints = new DataPoint[20];
        for (int i = 0;i<size;i++){
            temp = 1d * i;
            dataPoints[i] = new DataPoint(points[i],temp+lastX);
        }
        this.disk = dataPoints;
    }

    public void setMem(Double[] points,int size) {
        DataPoint[] dataPoints = new DataPoint[20];
        for (int i = 0;i<size;i++){
            temp = 1d * i;
            dataPoints[i] = new DataPoint(points[i],temp+lastX);
        }

        this.mem = dataPoints;
    }

    public void setResp(Double[] points,int size) {
        DataPoint[] dataPoints = new DataPoint[20];
        for (int i = 0;i<size;i++){
            temp = 1d * i;
            dataPoints[i] = new DataPoint(points[i],temp+lastX);
        }

        this.resp = dataPoints;
    }

    public void setRetn(Double[] points,int size) {
        DataPoint[] dataPoints = new DataPoint[20];
        for (int i = 0;i<size;i++){
            temp = 1d * i;
            dataPoints[i] = new DataPoint(points[i],temp+lastX);
        }

        this.retn = dataPoints;
    }

    public int getSrvno() {
        return srvno;
    }

    public void setSrvno(int srvno) {
        this.srvno = srvno;
    }

    public String getName() {
        return name;
    }

    public float getAvgDisk() {
        return avgDisk;
    }

    public float getAvgMem() {
        return avgMem;
    }

    public float getAvgResp() {
        return avgResp;
    }

    public float getAvgRetn() {
        return avgRetn;
    }

    public void setAvgDisk(float avgDisk) {
        this.avgDisk = avgDisk;
    }

    public void setAvgMem(float avgMem) {
        this.avgMem = avgMem;
    }

    public void setAvgResp(float avgResp) {
        this.avgResp = avgResp;
    }

    public void setAvgRetn(float avgRetn) {
        this.avgRetn = avgRetn;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
