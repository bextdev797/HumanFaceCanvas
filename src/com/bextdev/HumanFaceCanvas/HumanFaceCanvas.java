package com.bextdev.HumanFaceCanvas;

import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Canvas;

public class HumanFaceCanvas extends AndroidNonvisibleComponent {

    public HumanFaceCanvas(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction
    public void DrawHumanFaceInCanvas(Canvas canvas, String eyes, String mouth, String feeling, String gender) {
        float centerX = canvas.Width() / 2;
        float centerY = canvas.Height() / 2;
        float radius = Math.min(centerX, centerY) - 20;
        canvas.DrawCircle((int) centerX, (int) centerY, (int) radius, false);

        float eyeOffsetX = radius / 2.5f;
        float eyeOffsetY = radius / 3;
        float eyeSize = radius / 10;

        drawEye(canvas, centerX - eyeOffsetX, centerY - eyeOffsetY, eyes);
        drawEye(canvas, centerX + eyeOffsetX, centerY - eyeOffsetY, eyes);

        float mouthWidth = radius / 1.5f;
        float mouthHeight = radius / 3;
        drawMouth(canvas, centerX, centerY + mouthHeight / 2, mouthWidth, mouthHeight, mouth, feeling);

        drawEyebrows(canvas, centerX, centerY - radius, radius, feeling);

        drawGenderSpecificFeatures(canvas, centerX, centerY - radius, radius, gender);
    }

    private void drawEye(Canvas canvas, float x, float y, String eyeState) {
        float eyeSize = 20;
        switch (eyeState) {
            case "Wink":
                if (x < canvas.Width() / 2) {
                    // Draw closed eye for the left side
                    canvas.DrawLine((int) (x - eyeSize), (int) y, (int) (x + eyeSize), (int) y);
                } else {
                    // Draw open eye for the right side
                    canvas.DrawCircle((int) x, (int) y, (int) eyeSize, false);
                }
                break;
            case "Close":
                canvas.DrawLine((int) (x - eyeSize), (int) y, (int) (x + eyeSize), (int) y);
                break;
            case "Normal":
            default:
                canvas.DrawCircle((int) x, (int) y, (int) eyeSize, false);
                break;
        }
    }

    private void drawMouth(Canvas canvas, float x, float y, float width, float height, String mouthState, String feeling) {
        switch (feeling) {
            case "Happy":
                drawHappyMouth(canvas, x, y, width, height);
                break;
            case "Sad":
                drawSadMouth(canvas, x, y, width, height);
                break;
            case "Mad":
                drawMadMouth(canvas, x, y, width, height);
                break;
            case "Scared":
                drawScaredMouth(canvas, x, y, width, height);
                break;
            default:
                drawNormalMouth(canvas, x, y, width, height);
                break;
        }

        switch (mouthState) {
          case "Open":
              drawOpenMouth(canvas, x, y, width, height);
              break;
          default:
              drawNormalMouth(canvas, x, y, width, height);
              break;
        }
    }

    private void drawHappyMouth(Canvas canvas, float x, float y, float width, float height) {
        canvas.DrawLine((int) (x - width / 2), (int) y, (int) (x - width / 4), (int) (y + height / 2));
        canvas.DrawLine((int) (x - width / 4), (int) (y + height / 2), (int) (x + width / 4), (int) (y + height / 2));
        canvas.DrawLine((int) (x + width / 4), (int) (y + height / 2), (int) (x + width / 2), (int) y);
    }

    private void drawSadMouth(Canvas canvas, float x, float y, float width, float height) {
        canvas.DrawLine((int) (x - width / 2), (int) (y + height / 2), (int) (x - width / 4), (int) y);
        canvas.DrawLine((int) (x - width / 4), (int) y, (int) (x + width / 4), (int) y);
        canvas.DrawLine((int) (x + width / 4), (int) y, (int) (x + width / 2), (int) (y + height / 2));
    }

    private void drawMadMouth(Canvas canvas, float x, float y, float width, float height) {
        float openAmount = height / 4;
        canvas.DrawLine((int) (x - width / 2), (int) y, (int) (x - width / 4), (int) (y - openAmount));
        canvas.DrawLine((int) (x - width / 4), (int) (y - openAmount), (int) (x + width / 4), (int) (y - openAmount));
        canvas.DrawLine((int) (x + width / 4), (int) (y - openAmount), (int) (x + width / 2), (int) y);
    }

    private void drawScaredMouth(Canvas canvas, float x, float y, float width, float height) {
        float openAmount = height / 2;
        canvas.DrawLine((int) (x - width / 2), (int) y, (int) (x - width / 4), (int) (y - openAmount));
        canvas.DrawLine((int) (x - width / 4), (int) (y - openAmount), (int) (x + width / 4), (int) (y - openAmount));
        canvas.DrawLine((int) (x + width / 4), (int) (y - openAmount), (int) (x + width / 2), (int) y);
    }

    private void drawNormalMouth(Canvas canvas, float x, float y, float width, float height) {
        canvas.DrawLine((int) (x - width / 2), (int) y, (int) (x + width / 2), (int) y);
    }

    private void drawEyebrows(Canvas canvas, float x, float y, float radius, String feeling) {
     float eyebrowLength = radius / 2;
     float eyebrowY = y - radius / 2;  

     switch (feeling) {
        case "Mad":
            canvas.DrawLine((int) (x - eyebrowLength), (int) eyebrowY, (int) x, (int) (eyebrowY - eyebrowLength));
            canvas.DrawLine((int) x, (int) (eyebrowY - eyebrowLength), (int) (x + eyebrowLength), (int) eyebrowY);
            break;
        default:
            canvas.DrawLine((int) (x - eyebrowLength), (int) eyebrowY, (int) (x + eyebrowLength), (int) eyebrowY);
            break;
     }
  }


    private void drawGenderSpecificFeatures(Canvas canvas, float x, float y, float radius, String gender) {
        switch (gender) {
            case "Boy":
                drawShortHair(canvas, x, y, radius);
                break;
            case "Girl":
                drawLongHair(canvas, x, y, radius);
                break;
            default:
                break;
        }
    }

    private void drawShortHair(Canvas canvas, float x, float y, float radius) {
        float hairLength = radius / 3;
        canvas.DrawLine((int) (x - radius), (int) y, (int) (x + radius), (int) y);
        canvas.DrawLine((int) (x - radius), (int) y, (int) (x - radius + hairLength), (int) (y - hairLength));
        canvas.DrawLine((int) (x + radius), (int) y, (int) (x + radius - hairLength), (int) (y - hairLength));
    }

    private void drawLongHair(Canvas canvas, float x, float y, float radius) {
        float hairLength = radius / 1.5f;
        canvas.DrawLine((int) (x - radius), (int) y, (int) (x + radius), (int) y);
        canvas.DrawLine((int) (x - radius), (int) y, (int) (x - radius), (int) (y + hairLength));
        canvas.DrawLine((int) (x + radius), (int) y, (int) (x + radius), (int) (y + hairLength));
    }

    private void drawOpenMouth(Canvas canvas, float x, float y, float width, float height) {
     float radius = width / 2;
     float centerY = y + height / 4;  
     canvas.DrawCircle((int) x, (int) centerY, (int) radius, false);
   }


    @SimpleProperty
    public String EYE_WINK() {
        return "Wink";
    }

    @SimpleProperty
    public String EYE_CLOSE() {
        return "Close";
    }

    @SimpleProperty
    public String EYE_NORMAL() {
       return "Normal";
    }

    @SimpleProperty
    public String MOUTH_OPEN() {
     return "Open";
    }

    @SimpleProperty
    public String MOUTH_NORMAL() {
     return "Normal";
    }

    @SimpleProperty
    public String FEELING_NORMAL() {
     return "Normal";
    }

    @SimpleProperty
    public String FEELING_HAPPY() {
     return "Happy";
    }

    @SimpleProperty
    public String FEELING_SAD() {
     return "Sad";
    }

    @SimpleProperty
    public String FEELING_SCARED() {
     return "Scared";
    }

    @SimpleProperty
    public String FEELING_MAD() {
     return "Mad";
    }

    @SimpleProperty
    public String GENDER_BOY() {
     return "Boy";
    }

    @SimpleProperty
    public String GENDER_GIRL() {
     return "Girl";
    }
}

