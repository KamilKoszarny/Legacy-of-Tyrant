package model;


import model.character.Character;
import model.map.MapPiece;
import model.map.mapObjects.MapObject;
import viewIso.map.MapDrawCalculator;

import java.awt.*;

public class BattleEvent {

    private EventType type;
    private Point clickPoint, mapPoint;
    private MapPiece mapPiece;
    private MapObject object;
    private Character character;

    public BattleEvent(EventType type) {
        this.type = type;
    }

    public BattleEvent(EventType type, Point clickPoint) {
        this.type = type;
        this.clickPoint = clickPoint;
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

    public BattleEvent(EventType type, Character character) {
        this.type = type;
        this.character = character;
    }

    public BattleEvent(EventType type, Point clickPoint, Character character) {
        this.type = type;
        this.clickPoint = clickPoint;
        this.character = character;
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

    public Character getCharacter() {
        return character;
    }
}
