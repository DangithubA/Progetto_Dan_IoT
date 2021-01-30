package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecipeDescriptorOld {

    @JsonProperty("nome_ricetta")
    private String name;

    @JsonProperty("fase_1")
    private String phase1;

    @JsonProperty("temperatura_1")
    private Double temp1;

    @JsonProperty("tempo_1")
    private Double time1;

    @JsonProperty("fase_2")
    private String phase2;

    @JsonProperty("temperatura_2")
    private Double temp2;

    @JsonProperty("tempo_2")
    private Double time2;

    @JsonProperty("fase_3")
    private String phase3;

    @JsonProperty("temperatura_3")
    private Double temp3;

    @JsonProperty("tempo_3")
    private Double time3;

    @JsonProperty("fase_4")
    private String phase4;

    @JsonProperty("temperatura_4")
    private Double temp4;

    @JsonProperty("tempo_4")
    private Double time4;

    @JsonProperty("fase_5")
    private String phase5;

    @JsonProperty("temperatura_5")
    private Double temp5;

    @JsonProperty("tempo_5")
    private Double time5;


    public RecipeDescriptorOld() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhase1() {
        return phase1;
    }

    public void setPhase1(String phase1) {
        this.phase1 = phase1;
    }

    public Double getTemp1() {
        return temp1;
    }

    public void setTemp1(Double temp1) {
        this.temp1 = temp1;
    }

    public Double getTime1() {
        return time1;
    }

    public void setTime1(Double time1) {
        this.time1 = time1;
    }

    public String getPhase2() {
        return phase2;
    }

    public void setPhase2(String phase2) {
        this.phase2 = phase2;
    }

    public Double getTemp2() {
        return temp2;
    }

    public void setTemp2(Double temp2) {
        this.temp2 = temp2;
    }

    public Double getTime2() {
        return time2;
    }

    public void setTime2(Double time2) {
        this.time2 = time2;
    }

    public String getPhase3() {
        return phase3;
    }

    public void setPhase3(String phase3) {
        this.phase3 = phase3;
    }

    public Double getTemp3() {
        return temp3;
    }

    public void setTemp3(Double temp3) {
        this.temp3 = temp3;
    }

    public Double getTime3() {
        return time3;
    }

    public void setTime3(Double time3) {
        this.time3 = time3;
    }

    public String getPhase4() {
        return phase4;
    }

    public void setPhase4(String phase4) {
        this.phase4 = phase4;
    }

    public Double getTemp4() {
        return temp4;
    }

    public void setTemp4(Double temp4) {
        this.temp4 = temp4;
    }

    public Double getTime4() {
        return time4;
    }

    public void setTime4(Double time4) {
        this.time4 = time4;
    }

    public String getPhase5() {
        return phase5;
    }

    public void setPhase5(String phase5) {
        this.phase5 = phase5;
    }

    public Double getTemp5() {
        return temp5;
    }

    public void setTemp5(Double temp5) {
        this.temp5 = temp5;
    }

    public Double getTime5() {
        return time5;
    }

    public void setTime5(Double time5) {
        this.time5 = time5;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RecipeDescriptor{");
        sb.append("name='").append(name).append('\'');
        sb.append(", phase1='").append(phase1).append('\'');
        sb.append(", temp1='").append(temp1).append('\'');
        sb.append(", time1='").append(time1).append('\'');
        sb.append(", phase2='").append(phase2).append('\'');
        sb.append(", temp2='").append(temp2).append('\'');
        sb.append(", time2='").append(time2).append('\'');
        sb.append(", phase3='").append(phase3).append('\'');
        sb.append(", temp3='").append(temp3).append('\'');
        sb.append(", time3='").append(time3).append('\'');
        sb.append(", phase4='").append(phase4).append('\'');
        sb.append(", temp4='").append(temp4).append('\'');
        sb.append(", time4='").append(time4).append('\'');
        sb.append(", phase5='").append(phase5).append('\'');
        sb.append(", temp5='").append(temp5).append('\'');
        sb.append(", time5='").append(time5).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
