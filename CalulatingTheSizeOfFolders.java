import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

public class CalulatingTheSizeOfFolders {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (; ; ) {
            System.out.println("Введите путь до папки");
            String enter = sc.nextLine();
            Path path = Path.of(enter);
            long sizeDirectory = getSize(path);
            System.out.println("Размер папки = " + sizeDirectory);
            String[] multiplicity = {"байт", "Кб", "Мб", "Гб"};
            for (int i = 0; i < multiplicity.length; i++) {
                if (sizeDirectory / Math.pow(1024, i + 1) < 1 || i == multiplicity.length - 1) {
                    System.out.println("Размер папки по пути " + path + " = " + ((double) Math.round((double) sizeDirectory * 10 / Math.pow(1024, i))) / 10 + " " + multiplicity[i]);
                    break;
                }
            }

        }
    }

    private static long getSize(Path path) {
        AtomicLong size = new AtomicLong();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrib) {
                    size.addAndGet(attrib.size());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException ex) {
                    System.out.println("Пропущен файл: " + file + " ошибка: " + ex);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException ex) {
                    if (ex == null) {
                        return FileVisitResult.CONTINUE;
                    } else {
                        ex.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return size.get();
    }
}

