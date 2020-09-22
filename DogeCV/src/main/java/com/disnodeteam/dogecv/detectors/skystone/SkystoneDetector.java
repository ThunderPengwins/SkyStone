package com.disnodeteam.dogecv.detectors.skystone;

import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.DogeCVDetector;
import com.disnodeteam.dogecv.filters.DogeCVColorFilter;
import com.disnodeteam.dogecv.filters.GrayscaleFilter;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.disnodeteam.dogecv.scoring.ColorDevScorer;
import com.disnodeteam.dogecv.scoring.MaxAreaScorer;
import com.disnodeteam.dogecv.scoring.PerfectAreaScorer;
import com.disnodeteam.dogecv.scoring.RatioScorer;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class SkystoneDetector extends DogeCVDetector {
    public DogeCV.AreaScoringMethod areaScoringMethod = DogeCV.AreaScoringMethod.PERFECT_AREA; // Setting to decide to use MaxAreaScorer or PerfectAreaScorer

    //Create the default filters and scorers
    public DogeCVColorFilter blackFilter = new GrayscaleFilter(0, 25);
    public DogeCVColorFilter yellowFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW, 70); //Default Yellow blackFilter

    public RatioScorer ratioScorer = new RatioScorer(1.25, 0); // Used to find the short face of the stone
    public MaxAreaScorer maxAreaScorer = new MaxAreaScorer( 0.01);                    // Used to find largest objects
    public PerfectAreaScorer perfectAreaScorer = new PerfectAreaScorer(5000,.1); // Used to find objects near a tuned area value
    public ColorDevScorer colorDevScorer = new ColorDevScorer();
    //
    //
    // Results of the detector
    private Point screenPosition = new Point(); // Screen position of the mineral
    private Rect foundRect = new Rect(); // Found rect
    private Rect bothRect = new Rect(); //Top two
    private Rect bestAltRect = new Rect();
    private Rect secBestAltRec = new Rect();
    //
    private int bestBlack;
    //
    private Mat rawImage = new Mat();
    private Mat workingMat = new Mat();
    private Mat displayMat = new Mat();
    private Mat blackMask = new Mat();
    private Mat yellowMask = new Mat();
    private Mat hierarchy  = new Mat();
    //
    public String areaReturn;
    //Contraining
    public double constrainPosOffset = 100;   // How far from top is constrained               (Y position)
    //public double constrainSize      = 200;   // How wide is the margin of error for constraining
    
    public Point getScreenPosition() {
        return screenPosition;
    }
    
    public Rect foundRectangle() {
        return foundRect;
    }


    public SkystoneDetector() {
        detectorName = "Skystone Detector";
    }

    @Override
    public Mat process(Mat input) {
        input.copyTo(rawImage);
        input.copyTo(workingMat);
        input.copyTo(displayMat);
        input.copyTo(blackMask);

        // Imgproc.GaussianBlur(workingMat,workingMat,new Size(5,5),0);
        yellowFilter.process(workingMat.clone(), yellowMask);

        List<MatOfPoint> contoursYellow = new ArrayList<>();

        Imgproc.findContours(yellowMask, contoursYellow, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(displayMat,contoursYellow,-1,new Scalar(255,30,30),2);


        // Current result
        Rect bestRect = foundRect;
        Rect secBestRect = bestRect;
        double bestDifference = Double.MAX_VALUE; // MAX_VALUE since less difference = better

        // Loop through the contours and score them, searching for the best result
        for(MatOfPoint cont : contoursYellow){//score each yellow contour
            double score = calculateScore(cont); // Get the difference score using the scoring API

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(cont);
            //Imgproc.rectangle(displayMat, rect.tl(), rect.br(), new Scalar(0,0,255),2); // Draw rect

            // If the result is better then the previously tracked one, set this rect as the new best
            if(score < bestDifference){
                secBestRect = bestRect;
                bestDifference = score;
                bestRect = rect;
            }
        }

        Imgproc.rectangle(blackMask, bestRect.tl(), bestRect.br(), new Scalar(255,255,255), 1, Imgproc.LINE_4, 0);
        blackFilter.process(workingMat.clone(), blackMask);
        List<MatOfPoint> contoursBlack = new ArrayList<>();
        //
        Imgproc.findContours(blackMask, contoursBlack, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(displayMat,contoursBlack,-1,new Scalar(40,40,40),2);
        //
        //double constrainYMin = limitPositive(limitPositive(constrainPosOffset) - (constrainSize / 2));
        //double constrainYMax = (limitPositive(constrainPosOffset) + (constrainSize / 2));
        //
        if (contoursBlack.size() > 0){
            bestDifference = Double.MAX_VALUE;
        }
        bestBlack = 0;
        //
        double altarea = 0;
        double greatestArea = 0;
        double secGreatArea = 0;
        Rect altRect = new Rect();
        Rect secAltRec = new Rect();
        //
        for(MatOfPoint cont : contoursBlack){//score each black contour, override best yellow rect
            double score = calculateScore(cont); // Get the difference score using the scoring API
            //
            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(cont);
            //Imgproc.putText(displayMat,"Score: " + score, rect.tl(),0,1,new Scalar(60, 0, 255));
            Imgproc.rectangle(displayMat, rect.tl(), rect.br(), new Scalar(0,0,130),2); // Draw rect
            //
            altarea = ((rect.br().x - rect.tl().x) * (rect.br().y - rect.tl().y));
            if (altarea > greatestArea && rect.y > constrainPosOffset){
                greatestArea = altarea;
                altRect = rect;
            }
            if (altarea > greatestArea && rect.y > constrainPosOffset && rect.br().x > 20){
                secGreatArea = altarea;
                secAltRec = rect;
            }
            //
            // If the result is better then the previously tracked one, set this rect as the new best
            if(score < bestDifference){
                //if(rect.y < constrainYMax && rect.y > constrainYMin){
                secBestRect = bestRect;
                bestDifference = score;
                bestRect = rect;
                bestBlack++;
                //}
            }
        }
        //
        if (altRect != null){
            //
            Point conLeft = altRect.br();
            conLeft.x = 0;
            conLeft.y = constrainPosOffset;
            Point conRight = altRect.br();
            conRight.y = constrainPosOffset;
            //
            Imgproc.rectangle(displayMat,altRect.tl(),altRect.br(),new Scalar(255,166,0),4);
            Imgproc.line(displayMat,conLeft,conRight,new Scalar(255,255,255),6);
            bestAltRect = altRect;
            //
        }
        if (secAltRec != null){
            //
            Point conLeft = secAltRec.br();
            conLeft.x = 20;
            conLeft.y = 0;
            Point conRight = new Point();
            conRight.x = 20;
            conRight.y = 100;
            //
            Imgproc.rectangle(displayMat,secAltRec.tl(),secAltRec.br(),new Scalar(255,166,0),4);
            Imgproc.line(displayMat,conLeft,conRight,new Scalar(255,255,255),6);
            secBestAltRec = secAltRec;
            //
        }
        //
        if(bestRect != null) {
            // Show chosen result
            Imgproc.rectangle(displayMat, bestRect.tl(), bestRect.br(), new Scalar(255,30,180),4);
            Imgproc.putText(displayMat, "Chosen", bestRect.tl(),0,1,new Scalar(255,255,255));
            //
            //Imgproc.rectangle(displayMat, secBestRect.tl(), secBestRect.br(), new Scalar(208,0,255),4);
            //
            Point lineTop = bestRect.tl();
            lineTop.y = 0;
            Point lineBot = bestRect.tl();
            lineBot.y = 50;
            //
            Imgproc.line(displayMat,lineTop,lineBot,new Scalar(255,255,255),6);
            //
            areaReturn = Double.toString(Math.abs((bestRect.br().x - bestRect.tl().x) * (bestRect.br().y - bestRect.tl().y)));
            //
            screenPosition = new Point(bestRect.x, bestRect.y);
            foundRect = bestRect;
            found = true;
            //
            if (secBestRect != null){
                if (secBestRect.x > bestRect.x){
                    //
                    //Imgproc.rectangle(displayMat, bestRect.tl(), secBestRect.br(), new Scalar(52, 235, 61),1);
                    //Imgproc.putText(displayMat, "Both", bestRect.tl(),0,1,new Scalar(255,255,255));
                    bothRect.x = bestRect.x;
                    bothRect.y = bestRect.y;
                    bothRect.width = (int)(secBestRect.br().x - bestRect.x);
                    bothRect.height = (int)(secBestRect.br().y - bestRect.y);
                    //
                }else{
                    //Imgproc.rectangle(displayMat, secBestRect.tl(), bestRect.br(), new Scalar(52, 235, 61),1);
                    //Imgproc.putText(displayMat, "Both", secBestRect.tl(),0,1,new Scalar(255,255,255));
                    bothRect.x = secBestRect.x;
                    bothRect.y = secBestRect.y;
                    bothRect.width = (int)(bestRect.br().x - secBestRect.x);
                    bothRect.height = (int)(bestRect.br().y - secBestRect.y);
                }
            }
            //
        }
        else {
            //
            areaReturn = "N/A";
            //
            found = false;
        }
        //
        switch (stageToRenderToViewport) {
            case THRESHOLD: {
                Imgproc.cvtColor(blackMask, blackMask, Imgproc.COLOR_GRAY2BGR);

                return blackMask;
            }
            case RAW_IMAGE: {
                return rawImage;
            }
            default: {
                return displayMat;
            }
        }
    }
    //
    public String getArea(){
        return areaReturn;
    }
    //
    public double getBothX(){
        return bothRect.x + (bothRect.width / 2);
    }
    public double getBothY(){
        return bothRect.y + (bothRect.height / 2);
    }
    //
    public int getBestBlack(){
        return bestBlack;
    }
    //
    @Override
    public void useDefaults() {
        addScorer(ratioScorer);

        // Add diffrent scorers depending on the selected mode
        if(areaScoringMethod == DogeCV.AreaScoringMethod.MAX_AREA){
            addScorer(maxAreaScorer);
        }

        if (areaScoringMethod == DogeCV.AreaScoringMethod.PERFECT_AREA){
            addScorer(perfectAreaScorer);
        }

        if (areaScoringMethod == DogeCV.AreaScoringMethod.COLOR_DEVIATION){
            addScorer(colorDevScorer);
        }
    }
    //
    public double limitPositive(double input){
        return input > 0 ? input : 0;
    }
    //
    public double getAltRectx(){
        return bestAltRect.br().x - (bestAltRect.width / 2);
    }
    //
    public double getSecRectx(){
        return secBestAltRec.br().x - (secBestAltRec.width / 2);
    }
    //
    public double getAltRecty(){
        return bestAltRect.y;
    }
}
