package com.keburugaring.tompihariadi.monitoring.model;

/**
 * Created by tompihariadi on 20-Jan-15.
 */
public class Katalog {
    private String nama_file, nama_data, satelit, tanggal_akusisi, level;

    public String getNama_file() {
        return nama_file;
    }

    public void setNama_file(String nama_file) {
        this.nama_file = nama_file;
    }

    public String getSatelit() {
        return satelit;
    }

    public void setSatelit(String satelit) {
        this.satelit = satelit;
    }

    public String getTanggal_akusisi() {
        return tanggal_akusisi;
    }

    public void setTanggal_akusisi(String tanggal_akusisi) {
        this.tanggal_akusisi = tanggal_akusisi;
    }

    public String getNama_data(){
        return nama_data;
    }

    public void setNama_data(String nama_data){
        this.nama_data = nama_data;
    }

    public String getLevel(){
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Katalog(){

    }
    public Katalog(String nama_file, String satelit, String tanggal_akusisi, String nama_data, String level){
        this.nama_file = nama_file;
        this.satelit = satelit;
        this.tanggal_akusisi = tanggal_akusisi;
        this.nama_data = nama_data;
        this.level = level;
    }

    public Katalog(String nama_file, String satelit, String tanggal_akusisi){
        this.nama_file = nama_file;
        this.satelit = satelit;
        this.tanggal_akusisi = tanggal_akusisi;
    }


}
