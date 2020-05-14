package model;


import isoview.map.MapDrawCalculator;
import model.actions.attack.BodyPart;
import model.character.Character;
import model.map.MapPiece;
import model.map.mapObjects.MapObject;

import java.awt.*;

public class BattleEvent {

    private EventType type;
    private Point clickPoint, mapPoint;
    private MapPiece mapPiece;
    private MapObject object;
    private Character doingCharacter, subjectCharacter;
    private BodyPart bodyPart;

    public BattleEvent(EventType type) {
        this.type = type;
    }

    public BattleEvent(EventType type, Point clickPoint) {
        this.type = type;
        this.clickPoint = clickPoint;
    }

    public BattleEvent(EventType type, MapObject object) {
        this.type = type;
        this.object = object;
    }

    public BattleEvent(EventType type, MapObject object, Point mapPoint) {
        this.type = type;
        this.object = object;
        this.mapPoint = mapPoint;
    }

    public BattleEvent(EventType type, Point clickPoint, MapObject object) {
        this.type = type;
        this.clickPoint = clickPoint;
        this.object = object;
    }

    public BattleEvent(EventType type, Character subjectCharacter) {
        this.type = type;
        this.subjectCharacter = subjectCharacter;
    }

    public BattleEvent(EventType type, Character subjectCharacter, BodyPart bodyPart) {
        this.type = type;
        this.subjectCharacter = subjectCharacter;
        this.bodyPart = bodyPart;
    }

    public BattleEvent(EventType type, Point clickPoint, Character subjectCharacter) {
        this.type = type;
        this.clickPoint = clickPoint;
        this.subjectCharacter = subjectCharacter;
    }

    public BattleEvent(EventType type, Point clickPoint, Point mapPoint) {
        this.type = type;
        this.clickPoint = clickPoint;
        this.mapPoint = mapPoint;
    }

    public BattleEvent(EventType type, Point clickPoint, MapPiece mapPiece) {
        this.type = type;
        this.clickPoint = clickPoint;
        this.mapPiece = mapPiece;
    }

    public BattleEvent(EventType type, Point clickPoint, Point mapPoint, MapPiece mapPiece) {
        this.type = type;
        this.clickPoint = clickPoint;
        this.mapPoint = mapPoint;
        this.mapPiece = mapPiece;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public Point getClickPoint() {
        return clickPoint;
    }

    public Point getMapPoint() {
        if (mapPoint != null)
            return mapPoint;
        else if (clickPoint != null)
            return MapDrawCalculator.mapPointByClickPoint(clickPoint);
        return null;
    }

    public MapPiece getMapPiece() {
        return mapPiece;
    }

    public MapObject getObject() {
        return object;
    }

    public Character getDoingCharacter() {
        return doingCharacter;
    }

    public void setDoingCharacter(Character doingCharacter) {
        this.doingCharacter = doingCharacter;
    }

    public Character getSubjectCharacter() {
        return subjectCharacter;
    }

    public BodyPart getBodyPart() {
        return bodyPart;
    }
}
