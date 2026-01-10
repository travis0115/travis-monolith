package com.travis.monolith.module.demo.internal.utils;

import java.util.Random;

/**
 * 邀请码工具类
 *
 * @author travis
 * @date 2020-04-18 23:50
 */

public class InvitationCodeUtils {

    /**
     * 自定义进制(0,1没有加入,容易与o,l混淆)，数组顺序可进行调整增加反推难度，A用来补位因此此数组不包含A，共31个字符。
     */
    private static final char[] BASE = new char[]{'S', 'A', 'h', 'N', 'v', 'e', 'J', '8', 's', 'M', '2', 'd', 'G', 'z', 'x', 'V', '9', 'c', 'W', '7', 'C', 'p', 'K', '5', 'B', 'i', 'k', 'R', '3', 'a', 'Q', 'm', 'j', 'X', 'u', 'f', 'Y', 'r', 'F', '4', 'Z', 'U', 'w', 'D', 'y', 'T', 'n', 'E', '6', 'b', 'H', 'g', 'P', 'q'};

    /**
     * A补位字符，不能与自定义重复
     */
    private static final char SUFFIX_CHAR = 't';

    /**
     * 进制长度
     */
    private static final int BIN_LEN = BASE.length;

    /**
     * 生成邀请码最小长度
     */
    private static final int CODE_LEN = 6;


    /**
     * 生成邀请码
     *
     * @return code
     */
    public static String generate() {
        return generate(Long.valueOf(IdGenerator.nextId()));
    }

    /**
     * 生成邀请码
     *
     * @param id 唯一的id主键
     * @return code
     */
    public static String generate(Long id) {
        char[] buf = new char[BIN_LEN];
        int charPos = BIN_LEN;

        // 当id除以数组长度结果大于0，则进行取模操作，并以取模的值作为数组的坐标获得对应的字符
        while (id / BIN_LEN > 0) {
            int index = (int) (id % BIN_LEN);
            buf[--charPos] = BASE[index];
            id /= BIN_LEN;
        }
        buf[--charPos] = BASE[(int) (id % BIN_LEN)];
        // 将字符数组转化为字符串
        String result = new String(buf, charPos, BIN_LEN - charPos);

        // 长度不足指定长度则随机补全
        int len = result.length();
        if (len < CODE_LEN) {
            StringBuilder sb = new StringBuilder();
            sb.append(SUFFIX_CHAR);
            Random random = new Random();
            // 去除SUFFIX_CHAR本身占位之后需要补齐的位数
            for (int i = 0; i < CODE_LEN - len - 1; i++) {
                sb.append(BASE[random.nextInt(BIN_LEN)]);
            }
            result += sb.toString();
        }
        return result;
    }

    /**
     * 将邀请码解密成原来的id
     *
     * @param code 邀请码
     * @return id
     */
    public static Long decode(String code) {
        char[] charArray = code.toCharArray();
        long result = 0L;
        for (int i = 0; i < charArray.length; i++) {
            int index = 0;
            for (int j = 0; j < BIN_LEN; j++) {
                if (charArray[i] == BASE[j]) {
                    index = j;
                    break;
                }
            }

            if (charArray[i] == SUFFIX_CHAR) {
                break;
            }

            if (i > 0) {
                result = result * BIN_LEN + index;
            } else {
                result = index;
            }
        }
        return result;
    }

}
