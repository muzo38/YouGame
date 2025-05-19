import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SceneToolkit {

    public static void fadeInText(Graphics2D g2d, String text, Font font, Color color, float alpha, int x, int y) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clampAlpha(alpha)));
        g2d.setFont(font);
        g2d.setColor(color);
        g2d.drawString(text, x, y);
    }

    public static void drawCenteredText(Graphics2D g2d, String text, Font font, Color color, int panelWidth, int y) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2d.setFont(font);
        g2d.setColor(color);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int x = (panelWidth - metrics.stringWidth(text)) / 2;
        g2d.drawString(text, x, y);
    }

    public static void fadeInStoryText(Graphics2D g2d, String storyText, Font font, Color color, float alpha, int panelWidth, int panelHeight) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clampAlpha(alpha)));
        g2d.setFont(font);
        g2d.setColor(color);

        FontMetrics metrics = g2d.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
        List<String> lines = wrapText(storyText, metrics, panelWidth - 100); // 50px margin on each side

        int totalHeight = lines.size() * lineHeight;
        int startY = (panelHeight / 2) - (totalHeight / 2) - 100; // slightly above center

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineWidth = metrics.stringWidth(line);
            int x = (panelWidth - lineWidth) / 2;
            int y = startY + i * lineHeight;
            g2d.drawString(line, x, y);
        }
    }

    private static List<String> wrapText(String text, FontMetrics metrics, int maxWidth) {
        List<String> lines = new ArrayList<>();
    
        // First, split manually entered line breaks (\n)
        String[] rawLines = text.split("\n");

        for (String rawLine : rawLines) {
            String[] words = rawLine.trim().split(" ");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                String testLine = currentLine + (currentLine.length() > 0 ? " " : "") + word;
                if (metrics.stringWidth(testLine) > maxWidth) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    currentLine.append((currentLine.length() > 0 ? " " : "")).append(word);
                }
            }

            if (!currentLine.isEmpty()) {
                lines.add(currentLine.toString());
            }
        }

        return lines;
    }


    private static float clampAlpha(float alpha) {
        return Math.max(0f, Math.min(alpha, 1f));
    }

    public static void drawSceneTitle(Graphics2D g2d, String title, Font font, Color color, float alpha, int panelWidth, int panelHeight) {
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clampAlpha(alpha)));
    g2d.setFont(font);
    g2d.setColor(color);

    FontMetrics metrics = g2d.getFontMetrics(font);
    int titleWidth = metrics.stringWidth(title);
    int x = (panelWidth - titleWidth) / 2;
    int y = panelHeight / 2; // Centered vertically

    g2d.drawString(title, x, y);
    }

    public static void drawClickableText(Graphics2D g2d, String text, Font font, Color color, float alpha, int panelWidth, int y) {
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clampAlpha(alpha)));
    g2d.setFont(font);
    g2d.setColor(color);

    FontMetrics metrics = g2d.getFontMetrics(font);
    int x = (panelWidth - metrics.stringWidth(text)) / 2;

    g2d.drawString(text, x, y);
    g2d.drawLine(x, y + 2, x + metrics.stringWidth(text), y + 2); // underline
    }

    public static void drawSideBySideChoices(Graphics2D g2d, String leftText, String rightText, Font font, Color color, float alpha, int panelWidth, int y) {
    g2d.setFont(font);
    FontMetrics fm = g2d.getFontMetrics(font);

    int leftWidth = fm.stringWidth(leftText);
    int rightWidth = fm.stringWidth(rightText);

    int leftX = panelWidth / 4 - leftWidth / 2;
    int rightX = (panelWidth * 3) / 4 - rightWidth / 2;

    fadeInText(g2d, leftText, font, color, alpha, leftX, y);
    fadeInText(g2d, rightText, font, color, alpha, rightX, y);
    }

    public static Rectangle getCenteredTextBounds(Graphics g, String text, Font font, int centerX, int y) {
        FontMetrics fm = g.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int ascent = fm.getAscent();

        int x = centerX - textWidth / 2;
        int topY = y - ascent;

        return new Rectangle(x, topY, textWidth, textHeight);
    }
}
