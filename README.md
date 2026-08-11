# SmartAlertApp

A prototype of an intent-based smart alert application designed to help users control excessive mobile screen time. The application uses user intent, daily goals, quiet hours, and screen usage patterns to generate adaptive alerts and behavior suggestions.

## What it includes

- Intent-driven alert engine that adapts notifications based on user goals.
- Screen time summary dashboard with progress feedback.
- Settings panel for daily limit, quiet hours, and user intent.
- Modular React Native + Expo implementation.

## Architecture

- `src/App.tsx`: Main app shell and state management.
- `src/components/`: UI components for dashboard, alerts, and settings.
- `src/services/`: Business logic for screen time summarization and intent alert creation.
- `src/types/`: Shared TypeScript models.

## Run locally

1. Install dependencies: `npm install`
2. Start Expo: `npm start`
3. Open on Android/iOS emulator or Expo Go.

> This repository is a prototype and is intended for research, demonstration, and educational use.
