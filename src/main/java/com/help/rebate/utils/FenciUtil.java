package com.help.rebate.utils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;

/**
 * 分词工具
 */
public class FenciUtil {
    public static String fenci(String content) throws Exception {
        //String text = "珂莱蒂尔御寒保暖白鹅绒羽绒外套女中长款连帽黑色羽绒服冬季新款";
        List<Term> termList = HanLP.segment(content);

        String newFenci = "";
        for (Term term : termList) {
            if (term.word.trim().length() == 0) {
                continue;
            }
            else if (term.word.length() == 1) {
                newFenci += term.word;
            }
            else {
                newFenci += term.word + " ";
            }
        }
        return newFenci.trim();
    }
}
