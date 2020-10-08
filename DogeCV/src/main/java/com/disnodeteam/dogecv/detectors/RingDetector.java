package com.disnodeteam.dogecv.detectors;

import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.filters.DogeCVColorFilter;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RingDetector extends DogeCVDetector {
    public DogeCV.AreaScoringMethod areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Setting to decide to use MaxAreaScorer or PerfectAreaScorer
    //
    //Create the default filters and scorers
    //public DogeCVColorFilter filter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW, 70); //Default Yellow blackFilter
    public DogeCVColorFilter filter = new LeviColorFilter(LeviColorFilter.ColorPreset.ORANGE, 85);//Custom Orange test filter

    public int stonesToFind = 2;
    //
    private Rect foundRect = new Rect(); // Found rect
    private Rect bestRect = new Rect();
    //
    public RatioScorer ratioScorerForShortFace = new RatioScorer(1.25, 3); // Used to find the short face of the stone
    public RatioScorer ratioScorerForLongFace = new RatioScorer(0.625, 3); // Used to find the long face of the stone
    public MaxAreaScorer maxAreaScorer = new MaxAreaScorer( 5);                    // Used to find largest objects
    public PerfectAreaScorer perfectAreaScorer = new PerfectAreaScorer(5000,0.05); // Used to find objects near a tuned area value
    //
    // Results of the detector
    private ArrayList<Point> screenPositions = new ArrayList<>(); // Screen positions of the stones
    private ArrayList<Rect> foundRects = new ArrayList<>(); // Found rect
    //
    private Mat rawImage = new Mat();
    private Mat workingMat = new Mat();
    private Mat displayMat = new Mat();
    private Mat yellowMask = new Mat();
    private Mat hierarchy  = new Mat();
    //
    public RingDetector() {
        detectorName = "Ring Detector";
    }
    //
    @Override
    public Mat process(Mat input) {
        screenPositions.clear();
        foundRects.clear();
        //
        input.copyTo(rawImage);
        input.copyTo(workingMat);
        input.copyTo(displayMat);
        input.copyTo(yellowMask);
        //
        // Imgproc.GaussianBlur(workingMat,workingMat,new Size(5,5),0);
        filter.process(workingMat.clone(), yellowMask);
        //
        List<MatOfPoint> contoursYellow = new ArrayList<>();//list of yellow outlines
        Imgproc.findContours(yellowMask, contoursYellow, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(displayMat,contoursYellow,-1,new Scalar(230,70,70),2);
        //
        Collections.sort(contoursYellow, new Comparator<MatOfPoint>() {
            @Override
            public int compare(MatOfPoint matOfPoint, MatOfPoint t1) {
                return calculateScore(matOfPoint) > calculateScore(t1) ? 1 : 0;
            }
        });
        //
        List<MatOfPoint> subList = contoursYellow;
        //
        if (contoursYellow.size() > stonesToFind) {
            subList = contoursYellow.subList(0, stonesToFind);
        }
        //
        bestRect = foundRect;
        //
        for (MatOfPoint contour : subList) {
            Rect rect = Imgproc.boundingRect(contour);
            //
            // Show chosen result
            Imgproc.rectangle(displayMat, rect.tl(), rect.br(), new Scalar(255, 0, 0), 4);
            Imgproc.putText(displayMat, "Chosen", rect.tl(), 0, 1, new Scalar(255, 255, 255));
            //
            screenPositions.add(new Point(rect.x, rect.y));
            foundRects.add(rect);
            //
            if(foundRects.size() == 0 || rect.height > bestRect.height){
                bestRect = rect;
            }
        }
        //
        if(bestRect != null) {
            // Show chosen result
            Imgproc.rectangle(displayMat, bestRect.tl(), bestRect.br(), new Scalar(255,30,180),4);
            Imgproc.putText(displayMat, "Chosen", bestRect.tl(),0,1,new Scalar(255,255,255));
            //
            //Imgproc.rectangle(displayMat, secBestRect.tl(), secBestRect.br(), new Scalar(208,0,255),4);
            //
            foundRect = bestRect;
            found = true;
            //
        }
        else {
            found = false;
        }
        //
        switch (stageToRenderToViewport) {
            case THRESHOLD: {
                Imgproc.cvtColor(yellowMask, yellowMask, Imgproc.COLOR_GRAY2BGR);
                //
                return yellowMask;
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
    public Rect getRings(){
        return bestRect;
    }
    //
    @Override
    public void useDefaults() {
        addScorer(ratioScorerForShortFace);
        addScorer(ratioScorerForLongFace);
        //
        // Add diffrent scoreres depending on the selected mode
        if(areaScoringMethod == DogeCV.AreaScoringMethod.MAX_AREA){
            addScorer(maxAreaScorer);
        }
        //
        if (areaScoringMethod == DogeCV.AreaScoringMethod.PERFECT_AREA){
            addScorer(perfectAreaScorer);
        }
    }
}
