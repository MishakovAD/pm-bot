package home.bot;

import lombok.SneakyThrows;
import org.quartz.Scheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Application {

  @SneakyThrows
  public static void main(String[] args) {
    ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
    run.getBean(Scheduler.class).start();
  }












  public void test() {
    /**
     //         * Дана строка из нулей и единиц; нужно определить длину
     //         * максимального интервала из единиц при условии,
     //         * что можно удалить не более одного нуля из исходной строки.
     //         *
     //         *
     //         * 101010 → 2
     //         *
     //         * 10111011 → 5
     //         * 11000110 → 2
     //         * 01101001010 → 3
     //         */
//
////        String str = "01111011001111011";
//        String str = "011110110011111011";
//        int firstNONZero = str.indexOf('1');
//        List<Integer> sizeList = Arrays.stream(str.substring(firstNONZero, str.length()).split("0"))
//                .map(String::length)
//                .collect(Collectors.toList());
//        int currentLength = 0;
//        int maxLength = Integer.MIN_VALUE;
//        for (int i = 0; i < sizeList.size()-1; i++) {
//            currentLength = sizeList.get(i) + sizeList.get(i+1);
//            maxLength = Math.max(currentLength, maxLength);
//        }
//        System.out.println(maxLength);

//        String str = "01111011001111011";
//        String str = "011110110011111011";
//        String str = "10110000111";
//        String str = "101";
    String str = "1001";
    char[] chars = str.toCharArray();
    int currentLength = 0;
    int prevLength = 0;
    int maxLength = Integer.MIN_VALUE;
    int zeroCount = 0;

    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '1') {
        currentLength++;
      } else {
        if (zeroCount == 0) {
          zeroCount++;
          prevLength = currentLength;
          currentLength = 0;
        } else {
          zeroCount = 0;
          maxLength = Math.max(currentLength + prevLength, maxLength);
          prevLength = currentLength;
          currentLength = 0;
        }
      }
    }
    maxLength = Math.max(currentLength + prevLength, maxLength);
    System.out.println(maxLength);

//        /**
//         *
//         * Есть массив целых чисел и число K.
//         * Найти два таких (не обязательно различных) числа в массиве,
//         * сумма которых равна K,
//         * либо вывести, что таких чисел нет.
//         *
//         * [2, -4, 10], 6 → [-4, 10]
//         * [3, 6, 3], 6 → [3, 3]
//         * [3, 6], 6 → null
//         */
//
//        int[] nums = {-10, -1,  -15};
//        int target = -11;
//        int[] solution = new int[2];
//
//        Map<Integer, Long> map = Arrays.stream(nums)
//                .boxed()
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//        for (int i = 0; i < nums.length; i++) {
//            int find = target - nums[i];
//            Long val = map.getOrDefault(find, null);
//            if (val != null && (nums[i] != find || val > 1)) {
//                solution[0] = nums[i];
//                solution[1] = find;
//                break;
//            }
//        }
//        System.out.println(Arrays.stream(solution).boxed().map(String::valueOf).collect(Collectors.joining(", ")));

//        /**
//         * Даны две строки из латинских символов;
//         * проверить, что одну из другой можно получить не более чем одной заменой,
//         * вставкой или удалением одного символа.
//         *
//         * Например
//         * _("a", "b") => True
//         * _("aa", "bb") => False
//         * _("aa", "aba") => True
//         */
//
//        String str1 = "abc";
//        String str2 = "aabc";
//
//        String maxStr = str1.length() >= str2.length() ? str1 : str2;
//        String minStr = str1.length() < str2.length() ? str1 : str2;
//
//        if (maxStr.length() - minStr.length() > 1) {
//            System.out.println(false);
//        }
//
//        int replaceCount = 0;
//        if (maxStr.length() == minStr.length()) {
//            for (int i = 0; i < str1.length(); i++) {
//                if (maxStr.charAt(i) != minStr.charAt(i)) {
//                    replaceCount++;
//                }
//                if (replaceCount > 1) {
//                    System.out.println(false);
//                }
//            }
//        } else {
//            int addCount = 0;
//            int first = minStr.length()-1;
//            int second = maxStr.length()-1;
//            while (first >= 0) {
//                if (addCount > 1) {
//                    System.out.println(false);
//                    break;
//                }
//                char c1 = minStr.charAt(first);
//                char c2 = maxStr.charAt(second);
//                if (c1 == c2) {
//                    first--;
//                    second--;
//                } else if (second != 0 && c1 == maxStr.charAt(second-1)) {
//                    addCount++;
//                    second--;
//                } else {
//                    System.out.println(false);
//                    break;
//                }
//            }
//        }

//        /**
//         * Дано множество из N натуральных чисел.
//         * Необходимо выбрать его подмножество с максимальной четной суммой.
//         * Вывести эту сумму.
//         *
//         * findEvenSum([8, 9]) → 8
//         *
//         * findEvenSum([10, 17, 3]) → 30
//         * findEvenSum([1, 2, 3, 4]) → 10
//         */
//
//        Integer[] arr = new Integer[]{1, 3, 5, 7};
//        Arrays.sort(arr);
//        int sum = Stream.of(arr).mapToInt(Integer::intValue).sum();
//        if (sum % 2 == 0) {
//            System.out.println(sum);
//        } else {
//            System.out.println(sum - findFirstEven(arr));
//        }
//
//
//        /**
//         * Есть скобочное выражение с разными видами скобок {}, (), [], <>.
//         * Проверить, что оно правильное. Других символов, кроме скобок, быть не может.
//         *
//         * Input: ([{}])
//         * Output: true
//         *
//         * Input: ()(()){}[]
//         * Output: true
//         *
//         * Input: ((()))
//         * Output: true
//         *
//         * Input: ([[}])
//         * Output: false
//         *
//         * Input: ]]][[[
//         * Output: false
//         */
//
//        String str = "()(()){}[]";
////        String str = "()({()}()){}[]";
////        String str = "([{}])";
////        String str = "((()))";
////        String str = "([[}])";
////        String str = "]]][[[";
//        Stack<Character> stack = new Stack<>();
//        for (int i = 0; i < str.length(); i++) {
//            char current = str.charAt(i);
//            if (!isOpen(current) && stack.size() == 0) {
//                System.out.println(false);
//                break;
//            }
//            if (isOpen(current)) {
//                stack.add(current);
//            } else {
//                char prev = stack.pop();
//                char mustBe = getCloseChar(prev);
//                if (current != mustBe) {
//                    System.out.println(false);
//                    break;
//                }
//            }
//        }
//        System.out.println(true);
//
//
//
//
//        /**
//         * У нас имеется массив, состоящий из чисел и других массивов.
//         * Вложенные массивы также содержат в себе числа и массивы.
//         * Вложенность массивов может быть любой.
//         * Нам необходимо "склеить" все элементы исходного массива в одноуровневый массив.
//         * Порядок элементов должен сохраниться.
//         *
//         * Условия
//         *
//         * не использовать рекурсию
//         * не использовать готовые утилиты для flat массивов, которые доступны в языке
//         *
//         * // Input: listOf(1, 1, listOf(2, 3, listOf(1, listOf(3), 5), 2), 0)
//         * // Output: [1, 1, 2, 3, 1, 3, 5, 2, 0]
//         */
//
//        List<Object> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        List<Object> innerList1 = new ArrayList<>();
//        innerList1.add(4);
//        innerList1.add(5);
//        List<Object> innerList2 = new ArrayList<>();
//        innerList2.add(6);
//        innerList2.add(7);
//        innerList1.add(innerList2);
//        innerList1.add(8);
//        list.add(innerList1);
//        list.add(9);
//
//        List<Object> result = new ArrayList<>();
//        int size = list.size();
//        for (int i = 0; i < size; i++) {
//            Object o = list.get(i);
//            if (o instanceof Integer num) {
//                result.add(num);
//            } else {
//                List<Object> inner = (List<Object>) o;
//                list.remove(i);
//                list.addAll(i, inner);
//                size = size + inner.size() - 1;
//                --i;
//            }
//        }
//        System.out.println(result);
//        System.out.println("*********");
  }

}
