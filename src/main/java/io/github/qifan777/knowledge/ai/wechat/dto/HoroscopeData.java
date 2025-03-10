package io.github.qifan777.knowledge.ai.wechat.dto;

import lombok.Data;

@Data
public class HoroscopeData {
    private String date; // 时间 (Date)
    private String solarTerm; // 节气 (Solar Term)
    private String timePeriod; // 时辰 (Time Period)
    private Constellation constellation; // 星座运势 (Constellation Fortune)
    private String lunarDate; // 农历 (Lunar Date)
    private String heavenlyStemsAndEarthlyBranches; // 天干地支 (Heavenly Stems and Earthly Branches)

    // 内部类：Constellation
    @Data
    public static class Constellation {
        private String dailyQuote; // 今日一言 (Daily Quote)
        private Integer overallFortune; // 综合运势 (Overall Fortune)
        private String luckyColor; // 幸运颜色 (Lucky Color)
        private Integer luckyNumber; // 幸运数字 (Lucky Number)
        private String compatibleSign; // 速配星座
        // Getters and Setters



    }
    @Override
    public String toString() {
        return "星座运势数据 {\n" +
                "  时间='" + date + "',\n" +
                "  节气='" + solarTerm + "',\n" +
                "  时辰='" + timePeriod + "',\n" +
                "  农历='" + lunarDate + "',\n" +
                "  天干地支='" + heavenlyStemsAndEarthlyBranches + "',\n" +
                "  星座运势=" + constellationToString(constellation) + "\n" +
                "}";
    }
    private String constellationToString(Constellation constellation) {
        if (constellation == null) {
            return "null";
        }
        return "{\n" +
                "    今日一言='" + constellation.getDailyQuote() + "',\n" +
                "    综合运势=" + constellation.getOverallFortune() + ",\n" +
                "    幸运颜色='" + constellation.getLuckyColor() + "',\n" +
                "    幸运数字=" + constellation.getLuckyNumber() + ",\n" +
                "    速配星座='" + constellation.getCompatibleSign() + "'\n" +
                "  }";
    }

// 天干地支 (Heavenly Stems and Earthly Branches)
}
